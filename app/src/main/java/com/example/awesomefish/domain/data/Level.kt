package com.example.awesomefish.domain.data

import com.example.awesomefish.R
import com.example.awesomefish.shared.SoundManager

sealed class GameLevel(
    val id: Int,
    val enemyCount: Int,
    val foodReservoirSize: Int,
    val background: Int = R.drawable.level1,
    val levelTitle: String = "",
    val levelSound: SoundManager.BackgroundSound = SoundManager.BackgroundSound.WELCOME_SCREEN
) {
    class LevelOne :
        GameLevel(
            1,
            4,
            12,
            R.drawable.level1)
    class LevelTwo :
        GameLevel(
            2,
            6,
            18,
            R.drawable.level2,
            levelSound = SoundManager.BackgroundSound.LEVEL2
        )

    class LevelThree : GameLevel(
        3,
        9,
        24,
        background = R.drawable.level3,
        levelSound = SoundManager.BackgroundSound.LEVEL3
    )

    class LevelFour : GameLevel(
        4,
        12,
        30,
        background = R.drawable.level4,
        levelSound = SoundManager.BackgroundSound.LEVEL4
    )

    class LevelFive : GameLevel(
        5,
        15,
        36,
        background = R.drawable.level5,
        levelSound = SoundManager.BackgroundSound.LEVEL5
    )
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

