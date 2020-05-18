package com.example.awesomefish.domain.data

import com.example.awesomefish.domain.entities.Food

data class GameData(
    val level: GameLevel = GameLevel.LevelOne(),
    val isInProgress: Boolean = false,
    val score: Int = 0,
    val foodOnScreen: List<Food> = listOf(),
    val foodInReservoir: List<Food> = listOf(),
    val lifeCount: Int = 0
)
