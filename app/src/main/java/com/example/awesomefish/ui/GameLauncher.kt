package com.example.awesomefish.ui

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View
import com.example.awesomefish.scene.Scene
import com.example.awesomefish.scene.SceneManager
import com.example.awesomefish.shared.SoundManager
import com.example.awesomefish.ui.stages.StageOne

class GameLauncher(context: Context, soundManager: SoundManager) : View(context) {

    private var stageOne: Scene = StageOne(context, soundManager)
    private var running = true

    init {
        //This is a temp for now, eventually, we would load appropriate scene from stored progress
        SceneManager.addScene(stageOne)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (SceneManager.get().isRunning()) {
            SceneManager.get().update()
        }
        canvas?.let { theCanvas ->
            SceneManager.get().display(theCanvas)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return SceneManager.get().onTouch(event)
    }

    fun onPause() {
        SceneManager.get().onPause()
    }

    fun onResume() {
        SceneManager.get().onResume()
    }

    fun onDestroy() {
        SceneManager.get().onDestroy()
    }
}
