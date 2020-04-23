package com.example.awesomefish.ui.stages

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.MotionEvent
import com.example.awesomefish.R
import com.example.awesomefish.entities.Food
import com.example.awesomefish.entities.Player
import com.example.awesomefish.scene.Scene
import com.example.awesomefish.shared.FontManager
import com.example.awesomefish.shared.FoodManager
import com.example.awesomefish.shared.LifeFactory
import com.example.awesomefish.shared.SoundManager

class StageOne(context: Context, val soundManager: SoundManager) :
    Scene(context) {

    private var player: Player = Player(context, 0F, 0F, 0F, 0F)

    private val foods = FoodManager.createMuiltpleFood(context, FOOD_SIZE, FOOD_RESERVOIR_SIZE)

    val badFood = FoodManager.loadBadFood(context, 4)

    private var score = 0

    private var gameRunning: Boolean = true

    private val scorePaint = Paint()

    override fun isRunning() = gameRunning

    override fun stopRunning() {
        gameRunning = false
    }

    override fun startRunning() {
        gameRunning = true
    }

    override fun update() {
        player.update()

        if (FoodManager.hasFood()) {
            FoodManager.foods.forEach {
                it.update()
            }

            for (food in FoodManager.badFood) {
                food.update()
            }
        }
    }

    init {
        scorePaint.isAntiAlias = true
        scorePaint.color = Color.GREEN
        scorePaint.typeface = FontManager.getTypeForFont(context, FontManager.Font.SQUIRK)
        scorePaint.textSize = FontManager.FontSize.MEDIUM
    }

    override fun display(canvas: Canvas) {
        super.display(canvas)
        when {
            player.isDead() -> {
                //send game over event
            }
            else -> {
                setFoodPosition(canvas)
                drawPlayer(canvas)
                checkForCollision(canvas)
                drawFood(canvas)
                drawScore(canvas)
                drawLife(canvas)
            }
        }
    }

    private fun drawScore(canvas: Canvas) {
        canvas.drawText("Score - $score", MIN_SCORE_X, MIN_SCORE_Y, scorePaint)
    }

    var badFoodCount = 0
    private fun drawPlayer(canvas: Canvas) {
        player.maxX = canvas.width.toFloat()
        player.maxY = canvas.height.toFloat()

        player.draw(canvas)
    }

    private fun checkForCollision(canvas: Canvas) {
        for (index in 0 until FoodManager.size()) {
            if (player.hasEatenFood(FoodManager.foods[index])) {
                soundManager.playShortSound(
                    SoundManager.ShortSound.CLICK,
                    SoundManager.Loop.DONT_LOOP
                )
                val removed = FoodManager.removeFood(
                    FoodManager.foods[index]
                )
                if (removed) {
                    score += 5
                }
            }
        }

        for (index in 0 until FoodManager.badFood.size) {
            val badFood = FoodManager.badFood[index]

            if (player.hasEatenFood(badFood)) {
                badFood.foodX = canvas.width + badFood.foodWidth

                soundManager.playShortSound(
                    SoundManager.ShortSound.DAMAGE,
                    SoundManager.Loop.DONT_LOOP
                )

                player.reduceLife(1)

                println("eaten bad food ${badFoodCount++}")
            }
        }
    }

    private fun drawFood(canvas: Canvas) {
        if (FoodManager.hasFood()) {
            FoodManager.foods.forEach {
                it.draw(canvas)
            }

            for (food in FoodManager.badFood) {
                food.draw(canvas)
            }
        }
    }

    private fun drawLife(canvas: Canvas) {
        if (player.life > 0) {
            val lives = LifeFactory.produceLife(context, player.life, canvas.width)
            if (lives.isNotEmpty()) {
                lives.forEach {
                    it.draw(canvas)
                }
            }
        }
    }

    private fun setFoodPosition(canvas: Canvas) {
        FoodManager.foods.forEach {
            it.foodStartPostion = (canvas.width + FOOD_X_OFFSET)
            it.maxY = canvas.height
        }

        FoodManager.badFood.forEach {
            it.foodStartPostion = (canvas.width + FOOD_X_OFFSET)
            it.maxY = canvas.height
        }
    }

    override fun backgroundColor(): Int {
        return R.color.colorAccent
    }

    override fun backgroundImage(): Int? {
        return R.drawable.background
    }

    override fun onTouch(motinEvent: MotionEvent): Boolean {
        motinEvent.let {
            player.screenClicked = it.action == MotionEvent.ACTION_DOWN
            if (player.screenClicked) {
                player.pushUp()
            }
        }
        return true
    }

    override fun onPause() {
        gameRunning = false
    }

    override fun onResume() {
        gameRunning = true
    }

    private fun Player.hasEatenFood(food: Food): Boolean = Rect(
        playerX.toInt(),
        playerY.toInt(),
        (playerX + playerWidth).toInt(),
        (playerY + playerHeight).toInt()
    ).intersect(
        Rect(
            food.foodX.toInt(),
            food.foodY.toInt(),
            (food.foodX + food.foodY).toInt(),
            (food.foodY + food.foodHeight).toInt()
        )
    )

    override fun onDestroy() {
        soundManager.unload(SoundManager.MENU_1)
        SoundManager.clear()
    }

    companion object {
        const val MIN_SCORE_X = 10F
        const val MIN_SCORE_Y = 80F
        const val FOOD_X_OFFSET = 50F

        const val FOOD_SIZE = 6
        const val FOOD_RESERVOIR_SIZE = 12
    }
}
