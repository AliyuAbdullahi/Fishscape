package com.example.awesomefish.domain.entities

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.Log
import com.example.awesomefish.R

class Life(
    private val lifeContext: Context, private var lifeX: Float, private var lifeY: Float,
    foodContainingScreenWidth: Float = 0.0F,
    foodContainingScreenHeight: Float = 0.0F
) :
    Entity(lifeContext, lifeX, lifeY, foodContainingScreenWidth, foodContainingScreenHeight) {

    override fun update() {
        //Nothing here for now
    }

    override fun draw(canvas: Canvas) {
        imageResource()?.let {
            val bitmap = BitmapFactory.decodeResource(lifeContext.resources, it)
            canvas.drawBitmap(bitmap, lifeX, lifeY, null)
        }
    }

    fun width(): Int? =
        imageResource()?.let { BitmapFactory.decodeResource(lifeContext.resources, it).width }

    override fun updatePosition(x: Float, y: Float) {}

    override fun imageResource(): Int? = R.drawable.hearts

    fun printDebug() {
        Log.d("LIFE", "x: $lifeX, y: $lifeY")
    }

    override fun toString(): String {
        return "(Life=> x: $lifeX, y: $lifeY)"
    }
}
