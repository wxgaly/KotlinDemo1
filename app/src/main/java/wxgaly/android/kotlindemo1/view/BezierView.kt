package wxgaly.android.kotlindemo1.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * Created by wxgaly on 2017/9/26.
 */
class BezierView : View {

    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPath: Path = Path()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int)
            : super(context, attributeSet, defStyleAttr) {
        init()
    }

    private fun init() {

        //init the paint
        mPaint.isAntiAlias = true
        mPaint.isDither = true
        mPaint.style = Paint.Style.STROKE
        mPaint.color = Color.GREEN
        mPaint.strokeWidth = 10f

    }

    private fun initBezier() {
        val xPoints = arrayOf(0, 300, 200, 500, 700)
        val yPoints = arrayOf(0, 300, 700, 500, 1200)

        val progress : Float = 0.2f
    }

    /**
     * calculate bezier points at sometimes.
     * @param time time (0~1)
     * @param values bezier points (x or y)
     * @return the bezier point at the time
     */
    private fun calculateBezier(time : Float, values : FloatArray) : Float {

        val len = values.size

        for(x in len downTo 0){

        }

        return values[0]
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(mPath, mPaint)
    }

}