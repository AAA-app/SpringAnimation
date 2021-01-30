package com.aaa.springanimation.viewmodel

import android.annotation.SuppressLint
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import androidx.constraintlayout.widget.ConstraintSet
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {

    private fun createSpringAnimation(view: View, property: DynamicAnimation.ViewProperty, finalPosition: Float): SpringAnimation {
        val animation = SpringAnimation(view, property)
        val spring = SpringForce(finalPosition)
        spring.stiffness = SpringForce.STIFFNESS_MEDIUM
        spring.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
        animation.spring = spring
        return animation
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setUpAnimation(view: View, callback: () -> Unit) {
        lateinit var xAnimation: SpringAnimation
        lateinit var yAnimation: SpringAnimation

        view.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                xAnimation = createSpringAnimation(view = view, property =  SpringAnimation.X, finalPosition = view.x)
                yAnimation = createSpringAnimation(view = view, property =  SpringAnimation.Y, finalPosition = view.y)
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        var dx = 0f
        var dy = 0f
        val gestureDetector = GestureDetector(view.context, SingleTapConfirm())
        
        view.setOnTouchListener { touchView, event ->
            if (gestureDetector.onTouchEvent(event)) {
                callback.invoke()
            } else {
               when(event.actionMasked) {
                   MotionEvent.ACTION_DOWN -> {
                       dx = touchView.x - event.rawX
                       dy = touchView.y - event.rawY

                       xAnimation.cancel()
                       yAnimation.cancel()
                   }

                   MotionEvent.ACTION_MOVE -> {
                       view.animate()
                           .x(event.rawX + dx)
                           .y(event.rawY + dy)
                           .setDuration(0)
                           .start()
                   }

                   MotionEvent.ACTION_UP -> {
                       xAnimation.start()
                       yAnimation.start()
                   }
               }
            }
            true
        }
    }

}

private class SingleTapConfirm: GestureDetector.SimpleOnGestureListener() {
    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return true
    }
}