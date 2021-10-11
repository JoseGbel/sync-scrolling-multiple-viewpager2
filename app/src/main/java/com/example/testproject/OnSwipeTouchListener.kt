package com.example.testproject

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import java.lang.IllegalStateException

open class OnSwipeTouchListener(ctx: Context) : View.OnTouchListener {

    private val gestureDetector: GestureDetector

    var difference : Float? = null
    var startingXCoordinate : Float? = null

    init {
        gestureDetector = GestureDetector(ctx, GestureListener())
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if(event.action == MotionEvent.ACTION_DOWN){
            startingXCoordinate = event.rawX
        }
        if(event.action == MotionEvent.ACTION_MOVE) {
            difference = event.rawX - (startingXCoordinate ?: throw IllegalStateException("Starting coordinate was not defined"))
            onSwipe(difference)
        }
        return gestureDetector.onTouchEvent(event)
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }
    }

    open fun onSwipe(offset: Float?) {}
}