package com.example.awesomefish.shared

import android.content.Context
import com.example.awesomefish.domain.entities.Life

object LifeFactory {

    private var currentLife: Life? = null
    private const val START_INDEX = 150F
    private const val OFFSET = 50
    private const val LIFE_Y = 50F

    fun produceLife(context: Context, size: Int, rightRef: Int): List<Life> {
        val lives = mutableListOf<Life>()
        if (size == 0) {
            return listOf()
        }

        for (index in 0 until size) {
            if (index == 0) {
                currentLife = Life(context, rightRef - START_INDEX, LIFE_Y)
                lives.add(currentLife!!)
            } else {
                val life = currentLife
                life?.width()?.let { width ->
                    currentLife =
                        Life(context, rightRef - (START_INDEX + (width * index)), LIFE_Y)
                    lives.add(currentLife!!)
                }
            }
        }
        
        return lives
    }
}
