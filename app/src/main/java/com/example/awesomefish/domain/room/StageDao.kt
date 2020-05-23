package com.example.awesomefish.domain.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.awesomefish.domain.data.GameStage

@Dao
abstract class StageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertStage(stage: GameStage)

    @Query("DELETE FROM gamestage")
    abstract suspend fun deleteSavedStages()

    @Query("SELECT * FROM gamestage")
    abstract suspend fun getAllSavedStages(): List<GameStage>
}
