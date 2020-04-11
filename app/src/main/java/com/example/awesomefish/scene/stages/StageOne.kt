package com.example.awesomefish.scene.stages

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.MotionEvent
import com.example.awesomefish.R
import com.example.awesomefish.entities.Food
import com.example.awesomefish.entities.Player
import com.example.awesomefish.scene.Scene
import com.example.awesomefish.shared.foodmanager.FoodManager

class StageOne(context: Context) :
    Scene(context) {

    private var player: Player = Player(context, 0F, 0F, 0F, 0F)

    private val foods = FoodManager.createMuiltpleFood(context, 6, 12)

    override fun display(canvas: Canvas) {
        super.display(canvas)

        FoodManager.foods.forEach {
            it.foodStartPostion = (canvas.width + 50).toFloat()
            it.maxY = canvas.height
        }

        player.maxX = canvas.width.toFloat()
        player.maxY = canvas.height.toFloat()
        player.draw(canvas)

        for (index in 0 until FoodManager.size() - 1) {
            println(FoodManager.foods[index])
            if (player.hasEatenFood(FoodManager.foods[index])) {
                FoodManager.removeFood(FoodManager.foods[index])
            }
        }

        if (FoodManager.hasFood()) {
            FoodManager.foods.forEach {
                it.draw(canvas)
            }
        }
    }

    override fun backgroundColor(): Int {
        return R.color.colorAccent
    }

    override fun backgroundImage(): Int? {
        return R.drawable.background
    }

    override fun onTouch(event: MotionEvent): Boolean {
        event.let {
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
}
