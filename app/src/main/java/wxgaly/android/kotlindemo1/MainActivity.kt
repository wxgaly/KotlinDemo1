package wxgaly.android.kotlindemo1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mTouchMoveStartY: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        ll.setOnTouchListener { view, motionEvent -> onTouch(view, motionEvent) }
    }

    fun onTouch(view : View, motionEvent: MotionEvent) : Boolean {

        val action = motionEvent.action

        when (action) {

            MotionEvent.ACTION_DOWN -> {
                mTouchMoveStartY = motionEvent.y
               return true
            }

//            MotionEvent.ACTION_MOVE ->

            else -> return false
        }

        return false
    }

}
