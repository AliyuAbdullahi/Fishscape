package com.example.awesomefish.domain.data

object LocalStorageManager {
    var gameInProgress = false

    fun storeGameData() {}

    fun retrieveGameData(): GameData {
        return GameData()
    }
}

