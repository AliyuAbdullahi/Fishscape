package com.example.awesomefish.domain.entities

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.awesomefish.R
import com.example.awesomefish.shared.rand

const val MIN_Y = 200
const val FOOD_RADIUS = 30F
const val POISONOUS_FOOD_SIZE = 80F

class Food(
    val foodContext: Context,
    val type: Type,
    var foodX: Float,
    var foodY: Float,
    var maxY: Int = 0,
    var foodWidth: Float = 0F,
    var foodHeight: Float = 0F
) :
    Entity(foodContext, foodX, foodY, 0F, 0F, foodWidth, foodHeight) {

    private var prevY = foodY

    private val foodRadius: Int
        get() {
            val bitmap = BitmapFactory.decodeResource(foodContext.resources, imageResource())
            return bitmap.width
        }

    init {
        foodWidth = foodRadius.toFloat()
        foodHeight = foodRadius.toFloat()
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
        val foodBitmap = BitmapFactory.decodeResource(foodContext.resources, imageResource())
        canvas.drawBitmap(foodBitmap, foodX, foodY, null)
    }

    override fun imageResource(): Int =
        if (type == Type.EDIBLE) {
            R.drawable.good_food
        } else {
            R.drawable.bad_food
        }

    enum class Type {
        EDIBLE, POISON
    }
}
