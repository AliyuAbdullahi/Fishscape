package com.example.awesomefish.ui.gameover

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import com.example.awesomefish.R
import com.example.awesomefish.domain.scenebase.AbstractScene
import com.example.awesomefish.shared.*

class GameOverScene(context: Context) : AbstractScene(context) {

    private lateinit var gameOverClickedListener: GameOverClickListener

    private val scoreText = ScoreManager.score.scoreValue

    private val gameOverText = context.getString(R.string.game_over)

    private val newGameText = context.getString(R.string.new_game)

    private val quitGameText = context.getString(R.string.quit_game)

    private val mainMenuText = context.getString(R.string.main_menu)

    private val gameOverPaint = Paint()

    private val newGamePaint = Paint()

    private val scorePaint = Paint()

    private var gameOverTextX = 0
    private var gameOverTextY = 0

    private var newGameTextX = 0
    private var newGameTextY = 0

    private var quitGameTextX = 0
    private var quitGameTextY = 0

    private var scoreTextX = 0
    private var scoreTextY = 0

    private var mainMenuX = 0
    private var mainMenuY = 0

    override fun onAttach() {
        try {
            gameOverClickedListener = context as GameOverClickListener
            Log.d("SCREEN SIZE", "${ViewManager.width}")
        } catch (error: Throwable) {
            Log.e("ERROR", "Context should implement GameOverClickListener")
        }
    }

    init {
        onAttach()
        gameOverPaint.apply {
            isAntiAlias = true
            typeface = FontManager.getTypeForFont(context, FontManager.Font.SPACE_QUEST_XJ4O)
            textSize = FontManager.FontSize.LARGE
            color = Color.RED
        }

        newGamePaint.apply {
            isAntiAlias = true
            typeface = FontManager.getTypeForFont(context, FontManager.Font.SPACE_QUEST_XJ4O)
            textSize = FontManager.FontSize.BIG
            color = Color.YELLOW
        }

        scorePaint.apply {
            isAntiAlias = true
            typeface = FontManager.getTypeForFont(context, FontManager.Font.SPACE_QUEST_XJ4O)
            textSize = FontManager.FontSize.LARGE
            color = Color.BLUE
        }
    }

    override fun display(canvas: Canvas) {
        gameOverTextX =
            ((canvas.width / 2) - (gameOverText.length * FontManager.FontSize.LARGE) / 3).toInt()
        gameOverTextY = (15.toFloat() percent of number canvas.height.toFloat()).toInt()

        scoreTextX =
            ((canvas.width / 2) - ((SCORE_NAME.length + scoreText.toString().length - 1) * FontManager.FontSize.LARGE) / 3).toInt()
        scoreTextY = (30.toFloat() percent of number canvas.height.toFloat()).toInt()

        newGameTextX =
            ((canvas.width / 2) - (newGameText.length * FontManager.FontSize.BIG) / 3).toInt()
        newGameTextY = (45.toFloat() percent of number canvas.height.toFloat()).toInt()

        quitGameTextX =
            ((canvas.width / 2) - (quitGameText.length * FontManager.FontSize.BIG) / 3).toInt()
        quitGameTextY = (60.toFloat() percent of number canvas.height.toFloat()).toInt()

        mainMenuX =
            ((canvas.width / 2) - (mainMenuText.length * FontManager.FontSize.BIG) / 3).toInt()
        mainMenuY = (75.toFloat() percent of number canvas.height.toFloat()).toInt()


        val background = BitmapFactory.decodeResource(context.resources, R.drawable.splash)
        canvas.drawBitmap(background, 0F, 0F, null)

        canvas.drawText(
            gameOverText,
            gameOverTextX.toFloat(),
            gameOverTextY.toFloat(),
            gameOverPaint
        )

        canvas.drawText(
            "$SCORE_NAME${scoreText}",
            scoreTextX.toFloat(),
            scoreTextY.toFloat(),
            scorePaint
        )

        canvas.drawText(
            newGameText,
            newGameTextX.toFloat(),
            newGameTextY.toFloat(),
            newGamePaint
        )

        canvas.drawText(
            quitGameText,
            quitGameTextX.toFloat(),
            quitGameTextY.toFloat(),
            newGamePaint
        )

        canvas.drawText(
            mainMenuText,
            mainMenuX.toFloat(),
            mainMenuY.toFloat(),
            newGamePaint
        )
    }

    override fun onTouch(motionEvent: MotionEvent): Boolean {
        if (motionEvent.action == MotionEvent.ACTION_DOWN) {

            val xCoord = motionEvent.x
            val yCoord = motionEvent.y

            when {
                newGameText.rect(
                    newGameTextX - 150,
                    newGameTextY,
                    FontManager.FontSize.BIG.toInt()
                ).contains(
                    xCoord.toInt(),
                    yCoord.toInt()
                )
                -> {
                    gameOverClickedListener.newGameClicked()
                    return true
                }
                mainMenuText.rect(
                    mainMenuX - 150,
                    mainMenuY,
                    FontManager.FontSize.BIG.toInt()
                ).contains(xCoord.toInt(), yCoord.toInt()) -> {
                    gameOverClickedListener.mainMenuClicked()
                }
                quitGameText.rect(
                    quitGameTextX - 150,
                    quitGameTextY,
                    FontManager.FontSize.BIG.toInt()
                ).contains(
                    xCoord.toInt(),
                    yCoord.toInt()
                )
                -> {
                    gameOverClickedListener.quitGameClicked()
                    return true
                }
            }
        }
        return false
    }

    override fun update() {}

    interface GameOverClickListener {
        fun newGameClicked()
        fun quitGameClicked()
        fun mainMenuClicked()
    }

    companion object {
        private const val SCORE_NAME = "SCORE: "
    }
}
