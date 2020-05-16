package com.example.awesomefish

import android.app.Application
import android.content.Context

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this.applicationContext
    }

    companion object {
        lateinit var context: Context
    }
}
