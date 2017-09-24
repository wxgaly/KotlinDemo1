package wxgaly.android.kotlindemo1.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * Created by WXGALY on 2017/9/24.
 */
class BezierCurveView : View{

    private val mPaint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPath : Path = Path()

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


        mPaint.isAntiAlias = true
        mPaint.isDither = true
        mPaint.style = Paint.Style.STROKE
        mPaint.color = Color.GREEN
        mPaint.strokeWidth = 10f

        //the first order bezier curve
        mPath.moveTo(100F, 100F)
        mPath.lineTo(300f, 300f)

        //the second order bezier curve
//        mPath.quadTo(500f, 0f, 700f, 300f)

        //relative the last point
//        mPath.rQuadTo(200f, -300f, 400f, 0f)

        mPath.moveTo(300F, 600F)
        //the third order bezier curve
//        mPath.cubicTo(400f,400f,550f,1000f,600f,600f)
        mPath.rCubicTo(100f, -200f, 250f, 400f, 300f, 0f)


    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(mPath, mPaint)
    }


}