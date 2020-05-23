package com.example.awesomefish.domain.data

import com.example.awesomefish.R

sealed class GameLevel(
    val id: Int,
    val enemyCount: Int,
    val foodReservoirSize: Int,
    val background: Int = R.drawable.background,
    val levelTitle: String = ""
) {
    class LevelOne : GameLevel(1, 4, 12, R.drawable.background)
    class LevelTwo : GameLevel(2, 6, 18, R.drawable.level2)
    class LevelThree : GameLevel(3, 9, 24, R.drawable.level3)
    class LevelFour : GameLevel(4, 12, 30, R.drawable.level4)
    class LevelFive : GameLevel(5, 15, 36, R.drawable.level5)
}

fun levels() = listOf<GameLevel>(
    GameLevel.LevelOne(),
    GameLevel.LevelTwo(),
    GameLevel.LevelThree(),
    GameLevel.LevelFour(),
    GameLevel.LevelFive()
)

fun currentGameLevel(id: Int) = levels().find { it.id == id }

fun GameLevel.next() = levels()[if (id + 1 >= levels().size) 0 else id + 1]

fun GameLevel.isMax() = id == levels()[levels().size - 1].id

fun GameLevel.canGoNext() = isMax().not()

