package com.example.awesomefish.entities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.example.awesomefish.R

const val MIN_PLAYER_Y = 50F
const val MAX_PLAYER_Y_OFFSET = 200
const val MAX_POSSIBLE_LIFE = 6

class Player(
    var playerContext: Context,
    var playerX: Float,
    var playerY: Float = Float.MIN_VALUE,
    var maxX: Float,
    var maxY: Float,
    var playerWidth: Float = 0F,
    var playerHeight: Float = 0F
) : Entity(playerContext, playerX, playerY, 0F, 0F, playerWidth, playerHeight) {

    var screenClicked = false

    var speed = 15

    private var playerDead = false

    //This will change when we implement pause and resume and will be loaded from game data
    private var lifeCount = 3

    val life: Int
        get() = lifeCount

    private lateinit var playerImage: Bitmap

    override fun update() {
        if (playerDead.not()) {
            if (lifeCount <= 0) {
                playerDead = true
            }
            playerY = playerY + speed

            if (playerY >= (maxY - MAX_PLAYER_Y_OFFSET)) {
                playerY = (maxY - MAX_PLAYER_Y_OFFSET)
            }

            if (playerY < MIN_PLAYER_Y) {
                playerY = MIN_PLAYER_Y
            }

            speed += 5
        }
    }

    fun reduceLife(count: Int) {
        if (lifeCount > 0) {
            lifeCount -= count
        }
    }

    fun addLife(count: Int) {
        lifeCount += count
        lifeCount = lifeCount.coerceAtMost(MAX_POSSIBLE_LIFE)
    }

    fun isDead() = playerDead == true

    override fun draw(canvas: Canvas) {
        update()
        checkClickedAndDraw(playerContext, canvas)
        playerWidth = playerImage.width.toFloat()
        playerHeight = playerImage.height.toFloat()
    }

    private fun checkClickedAndDraw(context: Context?, canvas: Canvas) {
        context?.let {
            playerImage = BitmapFactory.decodeResource(context.resources, R.drawable.fish2)
            if (screenClicked) {
                canvas.drawBitmap(playerImage, playerX, playerY, null)
                screenClicked = false
            } else {
                imageResource()?.let { image ->
                    playerImage = BitmapFactory.decodeResource(context.resources, image)
                    println("Loaded... $playerX , $playerY ")
                    canvas.drawBitmap(playerImage, playerX, playerY, null)
                }
            }
        }
    }

    override fun updatePosition(x: Float, y: Float) {}

    fun pushUp() {
        speed = -25
    }

    override fun imageResource(): Int? = R.drawable.fish1

    fun printDebug() {
        println("X: ${this.playerX}, Y: ${this.playerY}, width: ${this.playerWidth}, height: ${this.playerHeight}")
    }
}
