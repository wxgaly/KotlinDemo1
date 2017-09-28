package wxgaly.android.kotlindemo1.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.support.v4.view.animation.PathInterpolatorCompat
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import wxgaly.android.kotlindemo1.constant.TAG

/**
 * Created by WXGALY on 2017/9/14.
 */
class TouchPullView : View {

    lateinit var mCirclePaint: Paint

    /**
     * the circle radius
     */
    private val mCircleRadius: Float = 50f

    private var mCirclePointX: Float = 0.0f
    private var mCirclePointY: Float = 0.0f

    /**
     *  the touch progress
     */
    private var mProgress: Float = 0.0f

    /**
     *  the height may be drag
     */
    private var mDragHeight: Int = 300

    /**
     * the target width
     */
    private var mTargetWidth: Int = 400

    /**
     *  bezier path and paint
     */
    private var mPath: Path = Path()
    lateinit var mPathPaint: Paint

    /**
     * the final height of gravity point, control the Y point
     */
    private var mTargetGravityHeight: Int = 10
    private var mTangentAngle: Int = 105
    private var mProgressInterpolator: Interpolator = DecelerateInterpolator()
    lateinit var mTanentAngleInterpolator: Interpolator
    private var valueAnimator: ValueAnimator? = null

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


        mPathPaint = Paint(Paint.ANTI_ALIAS_FLAG)

        mPathPaint.isAntiAlias = true

        mPathPaint.isDither = true

        mPathPaint.color = Color.RED

        mPathPaint.style = Paint.Style.FILL

        //path
        mTanentAngleInterpolator = PathInterpolatorCompat.create((mCircleRadius * 2.0f) /
                mDragHeight, 90.0f / mTangentAngle)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val count = canvas?.save()
        val tranX = (width - getValueByLine(width.toFloat(), mTargetWidth.toFloat(), mProgress)) / 2
        canvas?.translate(tranX, 0f)

        drawBezier(canvas)
        drawCircle(canvas)

        if (count != null) {
            canvas.restoreToCount(count)
        }
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
//        mCirclePointX = (width shr 1).toFloat()
//        mCirclePointY = (height shr 1).toFloat()

        updatePathLayout()
    }

    private fun drawCircle(canvas: Canvas?) {

        canvas?.drawCircle(mCirclePointX, mCirclePointY, mCircleRadius, mCirclePaint)

    }

    private fun drawBezier(canvas: Canvas?) {

        canvas?.drawPath(mPath, mPathPaint)

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

    /**
     *
     */
    private fun updatePathLayout() {

        val progress = mProgressInterpolator.getInterpolation(mProgress)

        val width = getValueByLine(width.toFloat(), mTargetWidth.toFloat(), mProgress)
        val height = getValueByLine(0f, mDragHeight.toFloat(), mProgress)

        val cPointX: Float = width / 2
        val cRadius = mCircleRadius
        val cPointY: Float = height - cRadius
        val endControlY = mTargetGravityHeight

        mCirclePointX = cPointX
        mCirclePointY = cPointY

        val path = mPath
        path.reset()
        path.moveTo(0f, 0f)

        var lEndPointX = 0.0
        var lEndPointY = 0.0

        var lControlPointX = 0.0
        var lControlPointY = 0f

        val angle = mTangentAngle * mTanentAngleInterpolator.getInterpolation(progress)
        val radian: Double = Math.toRadians(angle.toDouble())
        val x = Math.sin(radian) * cRadius
        val y = Math.cos(radian) * cRadius

        lEndPointX = cPointX - x
        lEndPointY = cPointY + y

        lControlPointY = getValueByLine(0f, endControlY.toFloat(), progress)
        val tHeight: Double = lEndPointY - lControlPointY
        val tWidth = tHeight / Math.tan(radian)
        lControlPointX = lEndPointX - tWidth

        path.quadTo(lControlPointX.toFloat(), lControlPointY, lEndPointX.toFloat(), lEndPointY.toFloat())

        path.lineTo((cPointX + (cPointX - lEndPointX)).toFloat(), lEndPointY.toFloat())

        path.quadTo((cPointX + cPointX - lControlPointX).toFloat(), lControlPointY, width, 0f)
    }

    /**
     * get the current value
     * @param start
     * @param end
     * @param progress
     */
    private fun getValueByLine(start: Float, end: Float, progress: Float): Float {

        return start + (end - start) * progress
    }

    fun release() {
        if (valueAnimator == null) {
            val animator: ValueAnimator = ValueAnimator.ofFloat(mProgress, 0f)
            animator.interpolator = DecelerateInterpolator()
            animator.duration = 200

            animator.addUpdateListener {
                valueAnimator ->
                val value = valueAnimator.getAnimatedValue()
                if (value is Float) {
                    setProgress(value)
                }
            }

            valueAnimator = animator
        } else {
            valueAnimator?.cancel()
            valueAnimator?.setFloatValues(mProgress, 0f)
        }

        valueAnimator?.start()
    }


}