package com.example.awesomefish.scene

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.MotionEvent
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.example.awesomefish.data.GameLevel

abstract class Scene(val context: Context) {

    open fun display(canvas: Canvas) {
        onResume()
        backgroundImage()?.let { image ->
            val bitmap = BitmapFactory.decodeResource(context.resources, image)
            canvas.drawBitmap(bitmap, 0F, 0F, null)
        }
    }

    open fun onAttach() {}

    @ColorRes
    abstract fun backgroundColor(): Int

    @DrawableRes
    abstract fun backgroundImage(): Int?

    abstract fun onPause()

    abstract fun update()

    abstract fun onResume()

    abstract fun onDestroy()

    abstract fun onTouch(motionEvent: MotionEvent): Boolean

    open fun setLevel(level: GameLevel) {}
}
