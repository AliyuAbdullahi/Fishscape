package com.example.awesomefish.entities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.example.awesomefish.R
import com.example.awesomefish.shared.Console

const val MIN_PLAYER_Y = 50F
const val MAX_PLAYER_Y_OFFSET = 200
const val MAX_POSSIBLE_LIFE = 6

class Player(
    private var playerContext: Context,
    var playerX: Float,
    var playerY: Float = Float.MIN_VALUE,
    var maxX: Float,
    var maxY: Float,
    var playerWidth: Float = 0F,
    var playerHeight: Float = 0F
) : Entity(playerContext, playerX, playerY, 0F, 0F, playerWidth, playerHeight) {

    var screenClicked = false

    var speed = PLAYER_SPEED

    private val playerDead
        get() = lifeCount == 0

    //This will change when we implement pause and resume and will be loaded from game data
    private var lifeCount = LIFE_COUNT

    val life: Int
        get() = lifeCount

    private lateinit var playerImage: Bitmap

    override fun update() {
        if (playerDead.not()) {
            if (lifeCount <= 0) {
                return
            }
            printDebug()
            playerY = playerY + speed

            if (playerY >= (maxY - MAX_PLAYER_Y_OFFSET)) {
                playerY = (maxY - MAX_PLAYER_Y_OFFSET)
            }

            if (playerY < MIN_PLAYER_Y) {
                playerY = MIN_PLAYER_Y
            }

            speed += SPEED_INCREMENT_VALUE
        }
    }

    fun reduceLife(count: Int) {
        if (lifeCount > 0 && lifeCount >= count) {
            lifeCount -= count
        } else {
            lifeCount = 0
        }
    }

    fun addLife(count: Int) {
        lifeCount += count
        lifeCount = lifeCount.coerceAtMost(MAX_POSSIBLE_LIFE)
    }

    fun isDead() = playerDead == true

    override fun draw(canvas: Canvas) {
        checkClickedAndDraw(playerContext, canvas)
        playerWidth = playerImage.width.toFloat()
        playerHeight = playerImage.height.toFloat()
    }

    private fun checkClickedAndDraw(context: Context?, canvas: Canvas) {
        context?.let {
            playerImage = BitmapFactory.decodeResource(context.resources, R.drawable.fish2)
            if (screenClicked) {
                canvas.drawBitmap(playerImage, playerX, playerY, null)
                Console.warn("Loaded... $playerX , $playerY ")
                screenClicked = false
            } else {
                imageResource()?.let { image ->
                    playerImage = BitmapFactory.decodeResource(context.resources, image)
                    canvas.drawBitmap(playerImage, playerX, playerY, null)
                }
            }
        }
    }

    override fun updatePosition(x: Float, y: Float) {
        //will be refactored. Entity position should be updated here
    }

    fun pushUp() {
        speed = -BUMP_VALUE
    }

    override fun imageResource(): Int? = R.drawable.fish1

    fun printDebug() {}

    companion object {
        private const val PLAYER_SPEED = 15
        private const val LIFE_COUNT = 3
        private const val BUMP_VALUE = 25
        private const val SPEED_INCREMENT_VALUE = 5
    }
}
