package by.aermakova.fancysquare

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.dynamicanimation.animation.FloatPropertyCompat
import kotlin.math.min

class Square(var side: Float, var centerX: Float, var centerY: Float) {

    private var sqColor: Int = Color.MAGENTA
        set(value) {
            field = value
            paint.color = value
        }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        this.color = sqColor
    }

    private var flingX: FlingAnimation? = null
    private var flingY: FlingAnimation? = null

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
        velocityX: Float,
        velocityY: Float
    ) {
        flingX = FlingAnimation(this, object : FloatPropertyCompat<Square>(SQUARE_TRANSLATION_X) {
            override fun setValue(`object`: Square?, value: Float) {
                `object`?.centerX = value
                view.invalidate()
            }

            override fun getValue(`object`: Square?): Float {
                return `object`?.centerX ?: 0f
            }

        }).apply {
            setStartVelocity(velocityX)
            setMinValue(side / 2)
            setMaxValue(view.width.toFloat() - side / 2)
            friction = FRICTION
            start()
        }

        flingY = FlingAnimation(this, object : FloatPropertyCompat<Square>(SQUARE_TRANSLATION_Y) {
            override fun setValue(`object`: Square?, value: Float) {
                `object`?.centerY = value
                view.invalidate()
            }

            override fun getValue(`object`: Square?): Float {
                return `object`?.centerY ?: 0f
            }

        }).apply {
            setStartVelocity(velocityY)
            setMinValue(side / 2)
            setMaxValue(view.height.toFloat() - side / 2)
            friction = FRICTION
            start()
        }
    }

    fun redraw(moveEvent: MotionEvent, view: View) {
        centerX = when {
            moveEvent.x < side / 2 -> side / 2
            moveEvent.x > view.width - side / 2 -> view.width - side / 2
            else -> moveEvent.x
        }
        centerY = when {
            moveEvent.y < side / 2 -> side / 2
            moveEvent.y > view.height - side / 2 -> view.height - side / 2
            else -> moveEvent.y
        }
    }

    fun scale(scaleFactor: Float, view: View) {
        if (side * scaleFactor > min(view.width, view.height) - OFFSET) {
            side = min(view.width, view.height).toFloat() - OFFSET
        } else {
            side *= scaleFactor
        }
        Log.d(DEBUG_TAG, "scale side: $side")
    }

    fun cancelAnim() {
        flingX?.cancel()
        flingY?.cancel()
    }

    fun touchOnSquare(event: MotionEvent?): Boolean {
        return event!!.x > centerX - side
                && event.x < centerX + side
                && event.y > centerY - side
                && event.y < centerY + side
    }

    companion object {
        private const val DEBUG_TAG = "Square"
        private const val FRICTION = 1.5f
        private const val OFFSET = 50f
        private const val SQUARE_TRANSLATION_X = "SquareTranslationX"
        private const val SQUARE_TRANSLATION_Y = "SquareTranslationY"
    }
}
