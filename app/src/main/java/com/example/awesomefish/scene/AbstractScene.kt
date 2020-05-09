package com.example.awesomefish.scene

import android.content.Context
import android.graphics.Color
import android.view.MotionEvent
import com.example.awesomefish.R

abstract class AbstractScene(context: Context) : Scene(context) {
    override fun isRunning(): Boolean {
        return true
    }

    override fun backgroundColor(): Int = R.color.colorAccent

    override fun backgroundImage(): Int? = null

    override fun onPause() {}

    override fun update() {}

    override fun onResume() {}

    override fun onDestroy() {}

    override fun onTouch(motionEvent: MotionEvent): Boolean = false

    override fun stopRunning() {}

    override fun startRunning() {}
}
