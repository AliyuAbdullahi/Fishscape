package com.example.awesomefish.entities

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.awesomefish.shared.rand

const val MIN_Y = 200
const val FOOD_SIZE = 30

class Food(
    context: Context,
    var foodX: Float,
    var foodY: Float,
    var maxY: Int = 0,
    val foodWidth: Float = FOOD_SIZE.toFloat(),
    val foodHeight: Float = FOOD_SIZE.toFloat()
) :
    Entity(context, foodX, foodY, 0F, 0F, foodWidth, foodHeight) {
    private val paint = Paint()

    private var prevY = foodY

    init {
        paint.color = Color.YELLOW
        paint.isAntiAlias = true
    }

    var speed = 15

    var foodStartPostion = 0F

    override fun update(): Pair<Float, Float> {

        if (foodX < 0) {
            foodX = foodStartPostion
            foodY = rand(MIN_Y, maxY).toFloat()
        }

        foodX = foodX - speed

        return Pair(foodX, foodY)
    }

    override fun updatePosition(x: Float, y: Float) {
        foodX = x
        foodY = y
    }

    override fun draw(canvas: Canvas) {
        update()
        canvas.drawCircle(foodX, foodY, FOOD_SIZE.toFloat(), paint)
    }

    override fun imageResource(): Int? {
        // do nothing
        return null
    }

}
