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
    private val mSrcPath: Path = Path()

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

        mSrcPath.rCubicTo(300f, 300f, 200f, 700f, 500f, 200f)

        Thread(Runnable {
            initBezier()

        }).start()


    }

    private fun initBezier() {



//        val xPoints : Array<Float> = arrayOf(0f, 300f, 200f, 500f, 700f)
//        val yPoints : Array<Float> = arrayOf(0f, 300f, 700f, 1200f, 200f)
        val xPoints : Array<Float> = arrayOf(0f, 300f, 200f, 500f)
        val yPoints : Array<Float> = arrayOf(0f, 300f, 700f, 200f)

        val fps = 1000

        val path = mPath

        for (i in 0 until fps) {

            val time : Float = i / fps.toFloat()
            val x = calculateBezier(time, xPoints)
            val y = calculateBezier(time, yPoints)
            path.lineTo(x, y)

            postInvalidate()

            Thread.sleep(16)
        }

    }

    /**
     * calculate bezier points at sometimes.
     * @param time time (0~1)
     * @param values bezier points (x or y)
     * @return the bezier point at the time
     */
    private fun calculateBezier(time: Float, values: Array<Float>): Float {

        val len = values.size

        for (x in len - 1 downTo 0) {
            for (y in 0 until x) {
                values[y] = values[y] + (values[y + 1] - values[y]) * time
            }
        }

        return values[0]
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        mPaint.color = 0x40000000
        canvas?.drawPath(mSrcPath, mPaint)

        mPaint.color = Color.RED
        canvas?.drawPath(mPath, mPaint)
    }

}