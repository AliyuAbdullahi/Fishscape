package com.example.awesomefish.shared

import com.example.awesomefish.entities.Food

object LocalStorageManager {
    var gameInProgress = false

    fun storeGameData() {

    }

    fun retrieveGameData(): GameData {
        return GameData()
    }
}

data class GameData(
    val isInProgress: Boolean = false,
    val score: Int = 0,
    val foodOnScreen: List<Food> = listOf(),
    val foodInReservoir: List<Food> = listOf(),
    val lifeCount: Int = 0
)
