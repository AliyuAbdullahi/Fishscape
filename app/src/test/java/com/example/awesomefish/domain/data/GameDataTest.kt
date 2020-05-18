package com.example.awesomefish.domain.data

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GameDataTest {
    lateinit var gameData: GameData

    @Before
    fun setUp() {
        gameData = GameData()
    }

    @Test
    fun testGetLevel() {
        assert(gameData.level is GameLevel.LevelOne)
    }

    @Test
    fun testIsInProgress() {
        Assert.assertFalse(gameData.isInProgress)
    }

    @Test
    fun testGetScore() {
        Assert.assertEquals(gameData.score, 0)
    }

    @Test
    fun getFoodOnScreen() {
        Assert.assertTrue(gameData.foodOnScreen.isEmpty())
    }

    @Test
    fun testGetFoodInReservoir() {
        Assert.assertTrue((gameData.foodInReservoir.isEmpty()))
    }
}
