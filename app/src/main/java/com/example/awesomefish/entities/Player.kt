package com.example.awesomefish.entities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.example.awesomefish.R

const val MIN_PLAYER_Y = 50F
const val MAX_PLAYER_Y_OFFSET = 200

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

    private lateinit var playerImage: Bitmap

    override fun update(): Pair<Float, Float> {
        playerY = playerY + speed

        if (playerY >= (maxY - MAX_PLAYER_Y_OFFSET)) {
            playerY = (maxY - MAX_PLAYER_Y_OFFSET)
        }

        if (playerY < MIN_PLAYER_Y) {
            playerY = MIN_PLAYER_Y
        }

        speed += 5

        return Pair(playerX, playerY)
    }

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
