package by.aermakova.fancysquare

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat

class AnimationPane @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    private val background = ColorDrawable().apply {
        color = Color.CYAN
    }

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
        Log.d("AnimationPane", "onDraw")
        animIcon.draw(canvas)
        background.draw(canvas)

        canvas.drawCircle(0f, 0f, 20f, paint )
        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minW = paddingLeft + paddingRight + animIcon.intrinsicWidth
        val w: Int = resolveSizeAndState(minW, widthMeasureSpec, 0)
        val minH = paddingTop + paddingBottom + animIcon.intrinsicHeight
        val h = resolveSizeAndState(minH, heightMeasureSpec, 0)
        setMeasuredDimension(w, h)
    }
}