package com.example.awesomefish.domain.repository

import com.example.awesomefish.di.DI
import com.example.awesomefish.domain.data.GameStage

class StageRepository : AbstractRepository<GameStage>() {
    private val stageDao = DI.provideGameStageDao()

    override suspend fun saveData(data: GameStage) {
        stageDao.insertStage(data)
    }

    override suspend fun getAll(): List<GameStage> = stageDao.getAllSavedStages()
}
