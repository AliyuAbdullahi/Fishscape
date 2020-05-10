package com.example.awesomefish.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.awesomefish.data.Score

@Database(entities = [Score::class], version = 1)
abstract class ScoreDb : RoomDatabase() {

    abstract fun scoreDao(): ScoreDao

    companion object {
        fun build(context: Context): ScoreDb = Room.databaseBuilder(
            context.applicationContext,
            ScoreDb::class.java,
            "score_db"
        ).build()
    }
}
