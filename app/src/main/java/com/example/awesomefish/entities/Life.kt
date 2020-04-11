package com.example.awesomefish.entities

import android.content.Context
import android.graphics.Canvas
import com.example.awesomefish.R

class Life(val lifeContext: Context, var lifeX: Float, var lifeY: Float) :
    Entity(lifeContext, lifeX, lifeY) {

    var active: Boolean = true

    override fun update(): Pair<Float, Float> {
        // Do nothing
        return Pair(lifeX, lifeY)
    }

    override fun draw(canvas: Canvas) {

    }

    override fun updatePosition(x: Float, y: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun imageResource(): Int? = if (active) R.drawable.hearts else R.drawable.heart_grey

}
