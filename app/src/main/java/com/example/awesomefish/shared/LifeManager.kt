package com.example.awesomefish.shared

import com.example.awesomefish.entities.Life

object LifeManager {

    private val lives = mutableListOf<Life>()

    var INDEX_OF_FIRST_LIFE = 0

    val playerLives: List<Life>
        get() = lives

    fun provideLife(vararg lives: Life): List<Life> {
        lives.forEach {
            this.lives.add(it)
        }

        return lives.asList()
    }

    fun removeLife(index: Int) {
        lives.removeAt(index)
    }

    fun addLife(life: Life) {
        lives.add(life)
    }

}

