package com.example.awesomefish.di

import android.content.Context
import com.example.awesomefish.App
import com.example.awesomefish.repository.ScoreRepository
import com.example.awesomefish.room.ScoreDb

object DI {

    private val context: Context = App.context

    fun provideScoreDb() = ScoreDb.build(context)

    fun provideScoreDao() = provideScoreDb().scoreDao()

    fun provideScoreRepository() = ScoreRepository()
}
