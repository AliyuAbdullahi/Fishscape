package com.example.awesomefish.domain.entities

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.awesomefish.shared.rand

const val MIN_Y = 200
const val FOOD_RADIUS = 30F
const val POISONOUS_FOOD_SIZE = 80F

class Food(
    context: Context,
    val type: Type,
    var foodX: Float,
    var foodY: Float,
    var maxY: Int = 0,
    val foodWidth: Float = FOOD_RADIUS,
    val foodHeight: Float = FOOD_RADIUS
) :
    Entity(context, foodX, foodY, 0F, 0F, foodWidth, foodHeight) {
    private val paint = Paint()

    private var prevY = foodY

    init {
        if (type == Type.EDIBLE) {
            paint.color = Color.YELLOW
        } else {
            paint.color = Color.RED
        }
        paint.isAntiAlias = true
    }

    var speed = if (type == Type.EDIBLE) 16 else 18

    var foodStartPostion = 0F

    override fun update() {

        if (foodX < 0) {
            foodX = foodStartPostion
            foodY = rand(MIN_Y, maxY).toFloat()
        }

        foodX = foodX - speed
    }

    override fun updatePosition(x: Float, y: Float) {
        foodX = x
        foodY = y
    }

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(foodX, foodY, FOOD_RADIUS, paint)
    }

    override fun imageResource(): Int? {
        // do nothing
        return null
    }

    enum class Type {
        EDIBLE, POISON
    }
}
