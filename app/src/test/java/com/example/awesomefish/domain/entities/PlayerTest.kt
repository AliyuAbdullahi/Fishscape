package com.example.awesomefish.domain.entities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PlayerTest {

    private val context = mockk<Context>(relaxed = true)

    private val canvas = mockk<Canvas>(relaxed = true)

    private val bitmap = mockk<Bitmap>(relaxed = true)

    private lateinit var player: Player

    @Before
    fun setUp() {
        mockkStatic(BitmapFactory::class)
        player = Player(
            context,
            PLAYER_X,
            PLAYER_Y,
            MAX_SCREEN_WIDTH,
            MAX_SCREEN_HEIGHT,
            PLAYER_WIDTH,
            PLAYER_HEIGHT
        )
    }

    @Test
    fun `player screen clicked is by default false`() {
        every { BitmapFactory.decodeResource(any(), any()) } returns bitmap
        player.screenClicked = true
        player.draw(canvas)
        Assert.assertFalse(player.screenClicked)
    }

    @Test
    fun `whenever a call is made to get player speed, 15 is returned`() {
        assertTrue(player.speed == 15)
    }

    @Test
    fun `player should have 3 lives by default`() {
        assertTrue(player.life == 3)
    }

    @Test
    fun `updating player increment speed by SPEED_UPDATE value`() {
        player.playerY = 3000F
        player.update()
        assertTrue(player.speed == 20)
        assertTrue(player.playerY == 1600F)
    }

    @Test
    fun reduceLife() {
        player.reduceLife(1)
        assertEquals(player.life, 2)
    }

    @Test
    fun `player can only have 0 as minimum life`() {
        player.reduceLife(20)
        assertEquals(player.life, 0)
    }

    @Test
    fun `whenever a value more than life limit is added to player life, player life is life limit`() {
        player.addLife(10)
        assertEquals(player.life, MAX_POSSIBLE_LIFE)
    }

    @Test
    fun `whenever a life value less than limit is added to life, player's life is incremented by value`() {
        player.addLife(1)
        assertEquals(player.life, 4)
    }

    @Test
    fun `whenever player's life is below life count, player is dead`() {
        player.reduceLife(4)
        assertTrue(player.isDead())
    }

    @Test
    fun `player speed is negative when pushUp method is called on player`() {
        player.pushUp()
        assertEquals(player.speed, -25)
    }

    companion object {
        const val PLAYER_X = 0F
        const val PLAYER_Y = 0F
        const val MAX_SCREEN_WIDTH = 800F
        const val MAX_SCREEN_HEIGHT = 1800F
        const val PLAYER_WIDTH = 32F
        const val PLAYER_HEIGHT = 32F
    }
}
