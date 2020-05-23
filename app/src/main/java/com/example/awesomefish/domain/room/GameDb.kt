package com.example.awesomefish.domain.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.awesomefish.domain.data.GameStage
import com.example.awesomefish.domain.data.GameStatus
import com.example.awesomefish.domain.data.Score

@Database(entities = [Score::class, GameStatus::class, GameStage::class], version = 1)
abstract class GameDb : RoomDatabase() {

    abstract fun scoreDao(): ScoreDao

    abstract fun gameStageDao(): StageDao

    abstract fun gameStatusDao(): GameStatusDao

    companion object {
        fun build(context: Context): GameDb = Room.databaseBuilder(
            context.applicationContext,
            GameDb::class.java,
            "game_db"
        ).build()
    }
}
