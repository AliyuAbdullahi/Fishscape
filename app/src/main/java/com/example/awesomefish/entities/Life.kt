package com.example.awesomefish.entities

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.Log
import com.example.awesomefish.R

class Life(
    val lifeContext: Context, var lifeX: Float, var lifeY: Float,
    var foodContainingScreenWidth: Float = 0.0F,
    var foodContainingScreenHeight: Float = 0.0F
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
