package com.example.awesomefish.shared

import android.content.Context
import com.example.awesomefish.entities.Food
import java.util.*
import kotlin.random.Random

object FoodManager {

    val foods = mutableListOf<Food>()
    private val foodReservoir = Stack<Food>()

    val badFood = mutableListOf<Food>()

    var foodSize = 0

    private fun addFood(food: Food) {
        if (food.type == Food.Type.EDIBLE) {
            foods.add(food)
            foodSize++
        }
    }

    fun loadBadFood(context: Context, range: Int = 2): MutableList<Food> {
        val foodStartX = 1200
        val foodEndX = 1900
        val foodStartY = 300
        val foodEndY = 920
        var offset = 300
        for (i in 1..range) {
            badFood.add(
                Food(
                    context, Food.Type.POISON,
                    rand(
                        foodStartX + offset++,
                        foodEndX
                    ).toFloat(),
                    rand(
                        foodStartY + offset++,
                        foodEndY
                    ).toFloat()
                )
            )
        }

        return badFood
    }

    private fun addToReservior(food: Food) {
        foodReservoir.push(food)
    }

    fun removeFood(food: Food): Boolean {
        val removed = foods.remove(food)
        removeFromReservoir()
        return removed
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

    fun createMuiltpleFood(
        context: Context,
        size: Int,
        reserviorSize: Int = size
    ) {
        val foodStartX = 600
        val foodEndX = 900
        val foodStartY = 200
        val foodEndY = 920

        var offset = 100

        for (index in 0 until size) {
            val food = Food(
                context,
                Food.Type.EDIBLE,
                rand(
                    foodStartX + offset++,
                    foodEndX
                ).toFloat(),
                rand(
                    foodStartY + offset++,
                    foodEndY
                ).toFloat()
            )

            addFood(food)
        }

        var reserVoirOffset = 80
        val reserviorFoodX = 3000
        for (index in 0 until reserviorSize) {
            val food = Food(
                context,
                Food.Type.EDIBLE,
                rand(
                    foodStartX + reserVoirOffset++,
                    reserviorFoodX
                ).toFloat(),
                rand(
                    foodStartY + reserVoirOffset++,
                    foodEndY
                ).toFloat()
            )

            addToReservior(food)
        }
    }

    fun take(size: Int) = foods.take(size)

    fun hasFood(): Boolean {
        return foodSize > 0
    }

    fun size() = foodSize
}

fun rand(s: Int, e: Int) = Random.nextInt(s, e + 1)
