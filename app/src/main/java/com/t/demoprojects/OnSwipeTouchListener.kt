package com.t.demoprojects

import android.annotation.SuppressLint
import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView

class OnSwipeTouchListener (ctx: Context) : View.OnTouchListener {

    private val gestureDetector: GestureDetector
    private var onSwipeCallback: OnSwipeCallback? = null

    private var isViewGroup: Boolean = false

    init {
        onSwipeCallback = ctx as OnSwipeCallback
        gestureDetector = GestureDetector(ctx, GestureListener())
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent?): Boolean {
        when (v) {
            is LinearLayout -> isViewGroup = true
            is AppCompatTextView -> isViewGroup = true
        }
        return gestureDetector.onTouchEvent(event)
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100

        override fun onDown(e: MotionEvent): Boolean {
            return isViewGroup
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float,
                             velocityY: Float): Boolean {
            var result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e1.x - e2.x
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight()
                        } else {
                            onSwipeLeft()
                        }
                        result = true
                    }
                }/* else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom()
                    } else {
                        onSwipeTop()
                    }
                    result = true
                }*/
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

            return result
        }
    }

    fun onSwipeRight() {
        onSwipeCallback?.onSwipeRight()
    }

    fun onSwipeLeft() {
        onSwipeCallback?.onSwipeLeft()
    }

    /*fun onSwipeTop() {
        onSwipeCallback?.onSwipeTop()
    }
    fun onSwipeBottom() {
        onSwipeCallback?.onSwipeBottom()
    }*/
}