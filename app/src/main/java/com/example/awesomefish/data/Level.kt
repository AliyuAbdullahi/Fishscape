package com.example.awesomefish.data

import com.example.awesomefish.R

sealed class GameLevel(
    val id: Int,
    val enemyCount: Int,
    val foodReservoirSize: Int,
    val background: Int = R.drawable.background
) {
    class LevelOne : GameLevel(1, 6, 12)
    class LevelTwo : GameLevel(2, 9, 18)
    class LevelThree : GameLevel(3, 12, 24)
    class LevelFour : GameLevel(4, 18, 32)
}
