package com.example.awesomefish.di

import android.content.Context
import com.example.awesomefish.App
import com.example.awesomefish.domain.repository.GameStatusRepository
import com.example.awesomefish.domain.repository.ScoreRepository
import com.example.awesomefish.domain.room.ScoreDb

object DI {

    private val context: Context = App.context

    private fun provideScoreDb() = ScoreDb.build(context)

    fun provideScoreDao() = provideScoreDb().scoreDao()

    fun provideScoreRepository() = ScoreRepository()

    fun provideGameStatusRepository() = GameStatusRepository()

    fun appContext() = context

    fun provideGameStatusDao() = provideScoreDb().gameStatusDao()
}
