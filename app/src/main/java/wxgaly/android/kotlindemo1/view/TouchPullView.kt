package wxgaly.android.kotlindemo1.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import wxgaly.android.kotlindemo1.constant.TAG

/**
 * Created by WXGALY on 2017/9/14.
 */
class TouchPullView : View {

    lateinit var mCirclePaint: Paint

    //the circle radius
    private val mCircleRadius: Float = 150f

    private var mCirclePointX: Float = 0.0f
    private var mCirclePointY: Float = 0.0f

    //the touch progress
    private var mProgress: Float = 0.0f

    //the height may be drag
    private var mDragHeight : Int = 800

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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode: Int = MeasureSpec.getMode(widthMeasureSpec)
        val width: Int = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode: Int = MeasureSpec.getMode(heightMeasureSpec)
        val height: Int = MeasureSpec.getSize(heightMeasureSpec)

        val iWidth: Int = (2 * mCircleRadius + paddingLeft + paddingRight).toInt()
        val iHeight: Int = ((mDragHeight * mProgress + 0.5f) + paddingTop + paddingBottom).toInt()

        var measuredWidth: Int = 0
        var measuredHeight: Int = 0

        when (widthMode) {
            MeasureSpec.EXACTLY -> measuredWidth = width
            MeasureSpec.AT_MOST -> measuredWidth = Math.min(iWidth, width)
            MeasureSpec.UNSPECIFIED -> measuredWidth = iWidth
        }

        when (heightMode) {
            MeasureSpec.EXACTLY -> measuredHeight = height
            MeasureSpec.AT_MOST -> measuredHeight = Math.min(iHeight, height)
            MeasureSpec.UNSPECIFIED -> measuredHeight = iHeight
        }

        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCirclePointX = (width shr 1).toFloat()
        mCirclePointY = (height shr 1).toFloat()
    }

    private fun drawCircle(canvas: Canvas?) {

        canvas?.drawCircle(mCirclePointX, mCirclePointY, mCircleRadius, mCirclePaint)

    }

    /**
     * set progress
     * @param progress the progress
     */
    fun setProgress(progress: Float) {
        mProgress = progress;
        Log.d(TAG, "progress : $progress")
        requestLayout()
    }


}