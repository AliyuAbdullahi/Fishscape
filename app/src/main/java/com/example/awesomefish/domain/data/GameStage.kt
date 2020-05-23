package com.example.awesomefish.domain.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class GameStage(@PrimaryKey val levelId: Int = 0, val stageName: String = "")


fun GameStage.toLevel(): GameLevel =
    levels().find { it.id == levelId } ?: GameLevel.LevelOne()
