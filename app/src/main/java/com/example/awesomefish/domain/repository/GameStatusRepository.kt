package com.example.awesomefish.domain.repository

import com.example.awesomefish.di.DI
import com.example.awesomefish.domain.data.GameStatus
import com.example.awesomefish.domain.room.GameStatusDao

class GameStatusRepository : AbstractRepository<GameStatus>() {

    private val gameStatusDao: GameStatusDao = DI.provideGameStatusDao()

    override suspend fun saveData(data: GameStatus) {
        gameStatusDao.saveGameStatus(data)
    }

    override suspend fun getAll(): List<GameStatus> {
        return gameStatusDao.getGameStatus()
    }

    override suspend fun deleteAll() {
        gameStatusDao.deleteGameStatus()
    }
}
