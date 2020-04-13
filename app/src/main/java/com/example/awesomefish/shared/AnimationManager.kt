package com.example.awesomefish.shared

import android.animation.*
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import kotlinx.android.synthetic.main.activity_splash.*

object AnimationManager {

    fun resetView(view: View) {
        view.setScaleX(1F)
        view.setScaleY(1F)
    }

    fun applyAnimationsOn(
        view: View,
        animationDuration: Long,
        animationRepeatCount: Int = ValueAnimator.INFINITE,
        animationRepeatMode: Int = ValueAnimator.REVERSE,
        vararg propertyHolder: PropertyValuesHolder,
        onComplete: () -> Unit = {}
    ) {

        ObjectAnimator.ofPropertyValuesHolder(view, *propertyHolder).apply {
            interpolator = AccelerateDecelerateInterpolator()

            duration = animationDuration

            repeatCount = animationRepeatCount

            repeatMode = animationRepeatMode

            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    onComplete()
                }
            })

            start()
        }
    }

}
