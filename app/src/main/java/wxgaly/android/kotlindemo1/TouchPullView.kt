package wxgaly.android.kotlindemo1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Created by WXGALY on 2017/9/14.
 */
class TouchPullView : View {

    lateinit var mCirclePaint : Paint

    private val mCircleRadius : Float = 150f

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

        mCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)

        mCirclePaint.isAntiAlias = true

        mCirclePaint.isDither = true

        mCirclePaint.color = Color.RED

        mCirclePaint.style = Paint.Style.FILL

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawCircle(canvas)
    }

    private fun drawCircle(canvas: Canvas?) {

        var x : Float = (width shr 1).toFloat()
        var y : Float = (height shr 1).toFloat()

        canvas?.drawCircle(x, y, mCircleRadius, mCirclePaint)

    }

}