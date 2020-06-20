package com.example.awesomefish.domain.repository

import com.example.awesomefish.domain.data.Score
import com.example.awesomefish.di.DI
import com.example.awesomefish.domain.room.ScoreDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ScoreRepository : Repository<Score> {

    private var cache = mutableSetOf<Score>()

    private val scoreDao: ScoreDao = DI.provideScoreDao()

    init {
        GlobalScope.launch {
            cache = getAll().toMutableSet()
        }
    }

    override suspend fun saveData(data: Score) {
        cache = getAll().toMutableSet()
        if (cache.size < 6) {
            cache.add(data)
            scoreDao.saveScore(data)
        } else {
            scoreDao.saveScores(addScore(data, cache.toMutableList()))
        }
    }

    override suspend fun saveListOfData(dataList: List<Score>) {
        scoreDao.saveScores(dataList)
    }

    override suspend fun getAll(): List<Score> = scoreDao.getScores()

    override suspend fun <R> getOne(filter: R): Score? {
        return null
    }

    override suspend fun deleteAll() {
        scoreDao.deleteAllScores()
    }

    fun getAllScoresFromMainThread(): List<Score> = cache.toList()

    private fun addScore(score: Score, list: MutableList<Score>): List<Score> {
        val temp = mutableListOf<Score>()
        var shouldReplace = false
        for (item in list) {
            if (score.scoreValue > item.scoreValue) {
                shouldReplace = true
            }
        }

        if (shouldReplace) {
            temp.add(score)
            for (x in 1 until list.size) {
                temp.add(list[x])
            }

            return temp.sortedBy { it.scoreValue }
        }

        return list
    }
}
