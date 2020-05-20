package com.example.awesomefish.ui.scene

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.MotionEvent
import com.example.awesomefish.R
import com.example.awesomefish.di.DI
import com.example.awesomefish.domain.data.GameLevel
import com.example.awesomefish.domain.data.Score
import com.example.awesomefish.domain.entities.Food
import com.example.awesomefish.domain.entities.Player
import com.example.awesomefish.domain.scenebase.Scene
import com.example.awesomefish.shared.*
import com.example.awesomefish.ui.GameLauncher
import com.example.awesomefish.ui.gameover.GameOverScene
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GameScene(context: Context, val soundManager: SoundManager) :
    Scene(context) {

    private val scoreRepo = DI.provideScoreRepository()

    private var gameLevel: GameLevel = GameLevel.LevelOne()

    private var player: Player = Player(context, 0F, 0F, 0F, 0F)

    private val foods =
        FoodManager.createMuiltpleFood(context, gameLevel.enemyCount, FOOD_RESERVOIR_SIZE)

    val badFood = FoodManager.loadBadFood(context, BAD_FOOD_SIZE)
    
    private var score = 0

    private val scorePaint = Paint()

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
        scorePaint.typeface = FontManager.getTypeForFont(context, FontManager.Font.SPACE_QUEST_XJ4O)
        scorePaint.textSize = FontManager.FontSize.BIG
    }

    override fun display(canvas: Canvas) {
        super.display(canvas)
        when {
            player.isDead() -> {
                ScoreManager.setTheScore(Score(score, System.currentTimeMillis()))
                saveScore(score)
                FoodManager.clearAll()
                GameLauncher.addScene(
                    GameOverScene(
                        context
                    )
                )
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

    fun saveScore(score: Int) {
        GlobalScope.launch {
            scoreRepo.saveData(Score(score, System.currentTimeMillis()))
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
        println("FOOD SIZE ${FoodManager.size()}")
        for (index in 0 until FoodManager.size() - 1) {
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
                player.isHurt = true
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

    override fun onTouch(motionEvent: MotionEvent): Boolean {
        motionEvent.let {
            player.screenClicked = it.action == MotionEvent.ACTION_DOWN
            if (player.screenClicked) {
                player.pushUp()
            }
        }
        return true
    }

    override fun onPause() {}

    override fun onResume() {}

    override fun setLevel(level: GameLevel) {
        this.gameLevel = level
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
        const val BAD_FOOD_SIZE = 4
        const val FOOD_RESERVOIR_SIZE = 12
    }
}
