package com.example.awesomefish.game

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View
import com.example.awesomefish.scene.Scene
import com.example.awesomefish.scene.SceneManager
import com.example.awesomefish.scene.stages.StageOne

class GameLauncher(context: Context) : View(context) {

    private var stageOne: Scene = StageOne(context)

    init {
        SceneManager.addScene(stageOne)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
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
}
