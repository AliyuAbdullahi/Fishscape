package com.example.awesomefish.shared.foodmanager

import android.content.Context
import com.example.awesomefish.entities.Food
import java.util.*
import kotlin.random.Random

object FoodManager {
    val foods = mutableListOf<Food>()
    private val foodReservoir = Stack<Food>()

    var foodSize = 0

    private fun addFood(food: Food) {
        foods.add(food)
        foodSize++
    }

    private fun addToReservior(food: Food) {
        foodReservoir.push(food)
    }

    fun removeFood(food: Food) {
        foods.remove(food)
        removeFromReservoir()
    }

    private fun removeFromReservoir() {
        if (foodReservoir.isEmpty().not()) {
            val currentFood = foodReservoir.pop()
            foods.add(currentFood)
        } else {
            foodSize--
        }
    }

    fun addFoods(vararg food: Food) {
        food.forEach { addFood(it) }
    }

    fun createMuiltpleFood(context: Context, size: Int, reserviorSize: Int = size) {
        val foodStartX = 600
        val foodEndX = 900
        val foodStartY = 200
        val foodEndY = 920

        var offset = 100

        for (index in 0 until size) {
            val food = Food(
                context,
                rand(foodStartX + offset++, foodEndX).toFloat(),
                rand(foodStartY + offset++, foodEndY).toFloat()
            )

            addFood(food)
        }

        var reserVoirOffset = 80
        val reserviorFoodX = 3000
        for (index in 0 until reserviorSize) {
            val food = Food(
                context,
                rand(foodStartX + reserVoirOffset++, reserviorFoodX).toFloat(),
                rand(foodStartY + reserVoirOffset++, foodEndY).toFloat()
            )

            addToReservior(food)
        }
    }

    fun take(size: Int) = foods.take(size)

    fun hasFood(): Boolean {
        return foodSize > 1
    }

    fun size() = foodSize
}

fun rand(s: Int, e: Int) = Random.nextInt(s, e + 1)
