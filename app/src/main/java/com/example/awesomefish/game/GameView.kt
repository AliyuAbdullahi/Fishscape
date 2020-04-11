package com.example.awesomefish.game

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.example.awesomefish.R
import kotlin.math.floor

private const val TOP_LIMIT = 100F

class GameView(context: Context) : View(context) {

    private var scoreValue = 0

    private var fishBitmap: Bitmap? = null

    private var background: Bitmap? = null

    private val score: Paint = Paint()

    private var fishX = 10F
    private var fishY = 10F

    private var canvasWidth = 0
    private var canvasHeight = 0

    private var fishImages = mutableListOf<Bitmap>()

    private var life = mutableListOf<Bitmap>()

    private var fishSpeed = 0

    private var touched = false

    private var yellowX = 0F
    private var yellowY = 0F

    private var yellowPaint = Paint()

    private var greenX = 0F
    private var greenY = 0F
    private var greenPaint = Paint()

    private var yellowBallSpeed = 15F
    private var greenBallSeep = 20F

    init {
        fishBitmap = BitmapFactory.decodeResource(resources, R.drawable.fish1)
        background = BitmapFactory.decodeResource(resources, R.drawable.background)
        score.color = Color.WHITE
        score.textSize = 70F
        score.typeface = Typeface.DEFAULT_BOLD
        score.isAntiAlias = true
        life.add(BitmapFactory.decodeResource(resources, R.drawable.hearts))
        life.add(BitmapFactory.decodeResource(resources, R.drawable.heart_grey))

        fishImages.add(BitmapFactory.decodeResource(resources, R.drawable.fish1))
        fishImages.add(BitmapFactory.decodeResource(resources, R.drawable.fish2))

        yellowPaint.color = Color.YELLOW

        fishY = 550F
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvasWidth = width
        canvasHeight = height

        val minFishY = fishImages[0].height + TOP_LIMIT
        val maxFishY = canvasHeight - (fishImages[0].height * 3).toFloat()

        fishY = fishY + fishSpeed

        println("Touched $fishY")

        if (fishY < minFishY) {
            fishY = minFishY
        }

        if (fishY > maxFishY) {
            fishY = maxFishY
        }

        fishSpeed = fishSpeed + 2
        yellowBallSpeed += 2

        background?.let {
            canvas?.drawBitmap(it, 0F, 0F, null)
        }

        canvas?.drawBitmap(life[0], 580F, 10F, null)
        canvas?.drawBitmap(life[0], 700F, 10F, null)
        canvas?.drawBitmap(life[0], 820F, 10F, null)

        if (touched) {
            canvas?.drawBitmap(fishImages[1], fishX, fishY, null)
            touched = false
        } else {
            canvas?.drawBitmap(fishImages[0], fishX, fishY, null)
        }

        yellowX = yellowX - yellowBallSpeed

        if(collissionChecker(yellowX, yellowY)) {
            scoreValue += 10
            yellowX -= 100
        }

        if(yellowX <= 0) {
            yellowX = (canvasWidth + 21).toFloat()
            yellowY = (floor(Math.random() * (maxFishY - minFishY)) + minFishY).toFloat()
        }

        canvas?.drawCircle(yellowY, yellowY, 30F, yellowPaint)

        canvas?.drawText("Score: $scoreValue", 20F, 60F, score)

    }

    private fun collissionChecker(x: Float, y: Float): Boolean {
        if(fishX < x && x < (fishX + fishImages[0].width) && fishY < y && y < (fishY + fishImages[0].height)) {
            return true
        }

        return false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            Toast.makeText(context, "Touched", Toast.LENGTH_SHORT).show()
            touched = true
            fishSpeed = -22
        }

        return true
    }
}
