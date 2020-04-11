package com.example.awesomefish.entities

import android.content.Context
import android.graphics.Canvas
import androidx.annotation.DrawableRes

abstract class Entity(
    val context: Context? = null,
    var x: Float = 0.0F,
    var y: Float = 0.0F,
    var containingScreenWidth: Float = 0.0F,
    var containingScreenHeight: Float = 0.0F,
    var entityWidth: Float = 0.0F,
    var entityHeight: Float = 0.0F
) {

    abstract fun draw(canvas: Canvas)

    abstract fun update(): Pair<Float, Float>

    abstract fun updatePosition(x: Float, y: Float)

    @DrawableRes
    abstract fun imageResource(): Int?

}
