package com.example.awesomefish.ui

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View
import com.example.awesomefish.domain.scenebase.Scene
import com.example.awesomefish.domain.scenebase.SceneManager
import com.example.awesomefish.shared.SoundManager
import com.example.awesomefish.ui.scene.GameScene

class GameLauncher(context: Context, soundManager: SoundManager) : View(context) {

    private var running = true

    init {
        //This is a temp for now, eventually, we would load appropriate scene from stored progress
        SceneManager.addScene(GameScene(context, soundManager))
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        SceneManager.get().update()
        canvas?.let { theCanvas ->
            SceneManager.get().display(theCanvas)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return SceneManager.get().onTouch(event)
    }

    fun onPause() {

    }

    fun onPause() {}

    fun onResume() {}

    fun onDestroy() {
        SceneManager.get().onDestroy()
    }

    companion object {
        fun addScene(scene: Scene) {
            SceneManager.addScene(scene)
        }
    }
}
