package com.example.awesomefish.data

object LocalStorageManager {
    var gameInProgress = false

    fun storeGameData() {}

    fun retrieveGameData(): GameData {
        return GameData()
    }
}

