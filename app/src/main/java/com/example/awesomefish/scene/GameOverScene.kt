package com.example.awesomefish.scene

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import com.example.awesomefish.R
import com.example.awesomefish.shared.*

class GameOverScene(context: Context) : AbstractScene(context) {

    private lateinit var gameOverClickedListener: GameOverClickListener

    private val scoreText = ScoreManager.score.scoreValue

    private val gameOverText = context.getString(R.string.game_over)

    private val newGameText = context.getString(R.string.new_game)

    private val quitGameText = context.getString(R.string.quit_game)

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

    override fun onAttach() {
        try {
            gameOverClickedListener = context as GameOverClickListener
        } catch (error: Throwable) {
            Log.e("ERROR", "Context should implement GameOverClickListener")
        }
    }

    init {
        onAttach()
        gameOverPaint.apply {
            typeface = FontManager.getTypeForFont(context, FontManager.Font.GLADIATOR_SPORT)
            textSize = FontManager.FontSize.LARGE
            color = Color.RED
        }

        newGamePaint.apply {
            typeface = FontManager.getTypeForFont(context, FontManager.Font.GLADIATOR_SPORT)
            textSize = FontManager.FontSize.MEDIUM
            color = Color.YELLOW
        }

        scorePaint.apply {
            typeface = FontManager.getTypeForFont(context, FontManager.Font.GLADIATOR_SPORT)
            textSize = FontManager.FontSize.LARGE
            color = Color.BLUE
        }
    }

    override fun display(canvas: Canvas) {
        gameOverTextX =
            ((canvas.width / 2) - (gameOverText.length * FontManager.FontSize.LARGE) / 2).toInt()
        gameOverTextY = (30.toFloat() percent of number canvas.height.toFloat()).toInt()

        scoreTextX =
            ((canvas.width / 2) - ((SCORE_NAME.length + scoreText.toString().length - 1) * FontManager.FontSize.LARGE) / 2).toInt()
        scoreTextY = (45.toFloat() percent of number canvas.height.toFloat()).toInt()

        newGameTextX =
            ((canvas.width / 2) - (newGameText.length * FontManager.FontSize.MEDIUM) / 2).toInt()
        newGameTextY = (60.toFloat() percent of number canvas.height.toFloat()).toInt()

        quitGameTextX =
            ((canvas.width / 2) - (quitGameText.length * FontManager.FontSize.MEDIUM) / 2).toInt()
        quitGameTextY = (75.toFloat() percent of number canvas.height.toFloat()).toInt()


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
    }

    override fun onTouch(motionEvent: MotionEvent): Boolean {
        if (motionEvent.action == MotionEvent.ACTION_DOWN) {

            val xCoord = motionEvent.x
            val yCoord = motionEvent.y

            when {
                newGameText.rect(
                    newGameTextX - 150,
                    newGameTextY,
                    FontManager.FontSize.MEDIUM.toInt()
                ).contains(
                    xCoord.toInt(),
                    yCoord.toInt()
                )
                -> {
                    Log.d("EVENT", "NEW GAME CLICKED")
                    gameOverClickedListener.newGameClicked()
                    return true
                }
                quitGameText.rect(
                    quitGameTextX - 150,
                    quitGameTextY,
                    FontManager.FontSize.MEDIUM.toInt()
                ).contains(
                    xCoord.toInt(),
                    yCoord.toInt()
                )
                -> {
                    Log.d("EVENT", "QUIT GAME CLICKED")
                    gameOverClickedListener.quitGameClicked()
                    return true
                }
            }
        }
        return false
    }

    override fun update() {}

    override fun stopRunning() {
        // do nothing
    }

    interface GameOverClickListener {
        fun newGameClicked()
        fun quitGameClicked()
    }

    companion object {
        private const val SCORE_NAME = "SCORE: "
    }
}
