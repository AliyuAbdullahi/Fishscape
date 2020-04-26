package com.example.awesomefish.scene

import android.content.Context
import android.view.MotionEvent

abstract class AbstractScene(context: Context) : Scene(context) {
    override fun isRunning(): Boolean {
        return true
    }

    override fun backgroundColor(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun backgroundImage(): Int? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPause() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update() {
    }

    override fun onResume() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTouch(motionEvent: MotionEvent): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stopRunning() {

    }

    override fun startRunning() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
