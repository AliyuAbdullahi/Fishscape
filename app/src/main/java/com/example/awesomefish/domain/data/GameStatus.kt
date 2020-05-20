package com.example.awesomefish.domain.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GameStatus(@PrimaryKey val id: Int, val paused: Boolean)
