package com.example.awesomefish.domain.room

import androidx.room.*
import com.example.awesomefish.domain.data.Score

@Dao
abstract class ScoreDao {

    @Insert
    abstract suspend fun saveScore(score: Score)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertScores(scores: List<Score>)

    @Query("SELECT * from score ORDER BY date")
    abstract suspend fun getScores(): List<Score>

    @Query("DELETE FROM score")
    abstract suspend fun deleteAllScores()

    @Transaction
    open suspend fun saveScores(scores: List<Score>) {
        deleteAllScores()
        insertScores(scores)
    }
}
