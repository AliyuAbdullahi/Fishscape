package com.example.awesomefish.shared

import com.example.awesomefish.domain.data.Score

object ScoreManager {

    private lateinit var score_: Score

    val score: Score
        get() = score_

    fun setTheScore(score: Score) {
        this.score_ = score
    }

}
