package com.example.awesomefish.domain.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Score(val scoreValue: Int, @PrimaryKey val date: Long) {
    override fun equals(other: Any?): Boolean {
        return (other as? Score)?.scoreValue == this.scoreValue
    }
}
