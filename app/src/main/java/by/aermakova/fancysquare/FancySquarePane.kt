package by.aermakova.fancysquare

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.core.view.GestureDetectorCompat

private const val DEBUG_TAG = "FancySquarePane"

class FancySquarePane @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    private val square = Square(
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            50f,
            context.resources.displayMetrics
        ), 500f, 500f
    )

    private val gestureDetector = GestureDetectorCompat(context, MyGestureListener(square, this))
    private val scaleDetector = ScaleGestureDetector(context, MyScaleListener(square, this))

    override fun onDraw(canvas: Canvas) {
        square.draw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (square.touchOnSquare(event)) {
            if (event.pointerCount > 1) {
                scaleDetector.onTouchEvent(event)
            } else {
                gestureDetector.onTouchEvent(event)
            }
        } else super.onTouchEvent(event)
    }
}

class MyScaleListener(private val square: Square, private val view: View) :
    ScaleGestureDetector.SimpleOnScaleGestureListener() {
    private var scaleFactor = 1f

    override fun onScale(detector: ScaleGestureDetector): Boolean {
        scaleFactor = detector.scaleFactor
        scaleFactor = 0.5f.coerceAtLeast(scaleFactor.coerceAtMost(2f))
        Log.d(DEBUG_TAG, "scaleFactor $scaleFactor")
        square.scale(scaleFactor, view)
        view.invalidate()
        return true
    }
}

class MyGestureListener(private val square: Square, private val view: View) :
    GestureDetector.SimpleOnGestureListener() {

    override fun onDown(event: MotionEvent): Boolean {
        square.cancelAnim()
        return true
    }

    override fun onScroll(
        downEvent: MotionEvent?,
        moveEvent: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        Log.d(DEBUG_TAG, "onScroll")
        square.redraw(moveEvent!!, view)
        view.invalidate()
        return true
    }

    override fun onFling(
        event1: MotionEvent,
        event2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        Log.d(DEBUG_TAG, "onFling")
        square.flingAnimation(view, velocityX, velocityY)
        return true
    }
}