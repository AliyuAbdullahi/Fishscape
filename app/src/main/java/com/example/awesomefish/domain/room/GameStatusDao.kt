package com.example.awesomefish.domain.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.awesomefish.domain.data.GameStatus

@Dao
abstract class GameStatusDao {

    @Query("SELECT * from gamestatus")
    abstract suspend fun getGameStatus(): List<GameStatus>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun saveGameStatus(gameStatus: GameStatus)

    @Query("DELETE FROM gamestatus")
    abstract suspend fun deleteGameStatus()
}
