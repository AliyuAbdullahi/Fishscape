package com.example.awesomefish.scene.stages

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
import com.example.awesomefish.shared.SoundManager

class StageOne(context: Context, val soundManager: SoundManager) :
    Scene(context) {

    private var player: Player = Player(context, 0F, 0F, 0F, 0F)

    private val foods = FoodManager.createMuiltpleFood(context, FOOD_SIZE, FOOD_RESERVOIR_SIZE)

    private var score = 0

    private val scorePaint = Paint()

    init {
        scorePaint.isAntiAlias = true
        scorePaint.color = Color.GREEN
        scorePaint.typeface = FontManager.getTypeForFont(context, FontManager.Font.GLADIATOR_SPORT)
        scorePaint.textSize = FontManager.FontSize.MEDIUM
//        soundManager.playShortSound(SoundManager.MENU_1, SoundManager.Loop.LOOP)
    }

    override fun display(canvas: Canvas) {
        super.display(canvas)

        FoodManager.foods.forEach {
            it.foodStartPostion = (canvas.width + FOOD_X_OFFSET)
            it.maxY = canvas.height
        }

        player.maxX = canvas.width.toFloat()
        player.maxY = canvas.height.toFloat()
        player.draw(canvas)

        for (index in 0 until FoodManager.size() - 1) {
            if (player.hasEatenFood(FoodManager.foods[index])) {
                val removed = FoodManager.removeFood(
                    FoodManager.foods[index]
                )
                if (removed) {
                    score += 5
                }
            }
        }

        if (FoodManager.hasFood()) {
            FoodManager.foods.forEach {
                it.draw(canvas)
            }
        }

        canvas.drawText("Score - $score", MIN_SCORE_X, MIN_SCORE_Y, scorePaint)
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
        //save data here
    }

    override fun onResume() {
        // Restore data here
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
