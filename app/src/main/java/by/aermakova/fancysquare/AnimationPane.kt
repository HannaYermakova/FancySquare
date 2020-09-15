package by.aermakova.fancysquare

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat

class AnimationPane @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    private val animIcon = ContextCompat.getDrawable(context, R.drawable.ic_emoji_people_24)!!
    var color: Int = Color.BLUE
        set(value) {
            field = value
            paint.color = value
        }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        animIcon.setBounds(0, 0, width, height);
        animIcon.draw(canvas);
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minW = paddingLeft + paddingRight + animIcon.intrinsicWidth
        val w: Int = resolveSizeAndState(minW, widthMeasureSpec, 0)
        val minH = paddingTop + paddingBottom + animIcon.intrinsicHeight
        val h = resolveSizeAndState(minH, heightMeasureSpec, 0)
        setMeasuredDimension(w, h)
    }

    fun startAnimation(from: Float, to: Float) {
        val animator = ObjectAnimator.ofFloat(this, TRANSLATION_Y, from, to).apply {
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            duration = 1000
            interpolator = AccelerateDecelerateInterpolator()
        }
        animator.start()
        invalidate()
    }
}