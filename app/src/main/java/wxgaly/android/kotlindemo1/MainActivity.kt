package wxgaly.android.kotlindemo1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val TOUCH_MOVE_MAX_Y: Float = 600f
    var mTouchMoveStartY: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {

        ll.setOnTouchListener { view, motionEvent -> onTouch(view, motionEvent) }
    }

    fun onTouch(view: View, motionEvent: MotionEvent): Boolean {

        val action = motionEvent.action

        when (action) {

            MotionEvent.ACTION_DOWN -> {
                mTouchMoveStartY = motionEvent.y
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                val y: Float = motionEvent.y
                if (y >= mTouchMoveStartY) {
                    val moveSize: Float = y - mTouchMoveStartY
                    val progress: Float =
                            if (moveSize >= TOUCH_MOVE_MAX_Y) 1f else moveSize / TOUCH_MOVE_MAX_Y
                    touchPullView.setProgress(progress)
                }
                return true
            }

            else -> {}
        }

        return false
    }


}
