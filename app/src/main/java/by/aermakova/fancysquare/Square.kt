package by.aermakova.fancysquare

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import kotlin.math.abs


private const val DEBUG_TAG = "Square"

class Square(var side: Float, var centerX: Float, var centerY: Float, view: View) {

    init {
        view.viewTreeObserver
            .addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    maxTranslationX = view.width - side
                    maxTranslationY = view.height - side
                    view.viewTreeObserver
                        .removeOnGlobalLayoutListener(this)
                }
            })
    }

    var maxTranslationX = 0f
    var maxTranslationY = 0f

    var sqColor: Int = Color.BLUE
        set(value) {
            field = value
            paint.color = value
        }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        this.color = sqColor
    }

    fun draw(canvas: Canvas?) {
        canvas?.drawRect(
            (centerX - side / 2),
            (centerY - side / 2),
            (centerX + side / 2),
            (centerY + side / 2),
            paint
        )
    }

    fun flingAnimation(
        view: View,
        downEvent: MotionEvent,
        moveEvent: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ) {

        FlingAnimation(view, DynamicAnimation.TRANSLATION_X).apply {
            setStartVelocity(velocityX)
            setMinValue(MIN_TRANSLATION)
            setMaxValue(maxTranslationX)
            friction = FRICTION
            start()
        }

        FlingAnimation(view, DynamicAnimation.TRANSLATION_Y).apply {
            setStartVelocity(velocityY)
            setMinValue(MIN_TRANSLATION)
            setMaxValue(maxTranslationY)
            friction = FRICTION
            start()
        }
    }

    fun redraw(event: MotionEvent) {
        Log.d(DEBUG_TAG, "redraw x: ${event.x} $maxTranslationX")
        Log.d(DEBUG_TAG, "redraw y: ${event.y} $maxTranslationY")

        if (event.x > side / 2 && event.x < maxTranslationX) {
            centerX = event.x
        }
        if (event.y > side / 2 && event.y < maxTranslationY) {
            centerY = event.y
        }
    }

    fun scale(scaleFactor: Float) {
        side *= scaleFactor
        Log.d(DEBUG_TAG, "scale side: $side")
    }

    companion object {
        private const val MIN_TRANSLATION = 0f
        private const val FRICTION = 1.1f
    }
}
