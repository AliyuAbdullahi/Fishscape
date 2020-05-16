package com.example.awesomefish.ui.start

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.awesomefish.R
import com.example.awesomefish.data.LocalStorageManager
import com.example.awesomefish.shared.AnimationManager
import com.example.awesomefish.shared.FontManager
import com.example.awesomefish.shared.SoundManager
import com.example.awesomefish.ui.dialogs.HighscoreDialog
import com.example.awesomefish.ui.stages.GameHostActivity
import kotlinx.android.synthetic.main.activity_splash.*


class StartActivity : AppCompatActivity() {


    private lateinit var soundManager: SoundManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setUpSound()
        setUpFont()
        setUpView()
        applyAnimationOnView()
        processStartGame()
        setUpClickListener()
    }



    private fun processStartGame() {
        startGame.setOnClickListener {
            val zoomX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.3F, 0.8F)
            val zoomY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.3F, 0.8F)
            val zoomAnimationPropertyHolder = arrayOf(zoomX, zoomY)
            AnimationManager.applyAnimationsOn(
                startGame,
                ANIMATION_DURATION,
                animationRepeatCount = 0,
                animationRepeatMode = ValueAnimator.REVERSE,
                propertyHolder = *zoomAnimationPropertyHolder
            ) {
                AnimationManager.resetView(startGame)
                soundManager.stopBackgroundSound()
                startActivity(Intent(this, GameHostActivity::class.java))
                this.finish()
            }
            soundManager.playShortSound(
                SoundManager.ShortSound.REVEAL_TWO,
                SoundManager.Loop.DONT_LOOP
            )
        }
    }

    private fun setUpClickListener() {
        highScore.setOnClickListener {
            HighscoreDialog.show(supportFragmentManager)
        }
    }

    private fun setUpFont() {
        splashTitle.setTypeface(FontManager.getTypeForFont(this, FontManager.Font.SPACE_QUEST_XJ4O))
        startGame.setTypeface(FontManager.getTypeForFont(this, FontManager.Font.SQUIRK))
        highScore.setTypeface(FontManager.getTypeForFont(this, FontManager.Font.SQUIRK))
    }

    private fun applyAnimationOnView() {
        val translateY =
            PropertyValuesHolder.ofFloat(
                View.TRANSLATION_Y, -FISH_TRANSLATION,
                FISH_TRANSLATION
            )
        val rotate = PropertyValuesHolder.ofFloat(
            View.ROTATION_Y,
            FISH_ROTATION
        )
        val alpha = PropertyValuesHolder.ofFloat(
            View.ALPHA,
            TEXT_MIN_ALPHA,
            TEXT_MAX_ALPH
        )
        val properties = arrayOf(translateY, rotate)
        val startGameProperties = arrayOf(alpha)
        AnimationManager.applyAnimationsOn(
            fishImage,
            ANIMATION_DURATION,
            propertyHolder = *properties
        )
        AnimationManager.applyAnimationsOn(
            startGame,
            ANIMATION_DURATION,
            propertyHolder = *startGameProperties
        )
    }

    override fun onResume() {
        super.onResume()
        soundManager.resume()
    }

    override fun onPause() {
        super.onPause()
        soundManager.pauseBackgroundSound()
    }

    private fun setUpSound() {
        soundManager = SoundManager.instance(this)
        soundManager.playLongTrack(SoundManager.BackgroundSound.HOME_SOUND)
    }

    private fun setUpView() {
        resumeGame.visibility = if (LocalStorageManager.gameInProgress) View.VISIBLE else View.GONE
        startGame.text =
            if (LocalStorageManager.gameInProgress) getString(R.string.resume_game) else getString(
                R.string.start_game
            )
    }

    fun startGameScreen() {
        Handler().postDelayed(
            {
                startActivity(
                    Intent(
                        this@StartActivity,
                        GameHostActivity::class.java
                    )
                )
            },
            SPLASH_WAIT_TIME
        )
    }

    companion object {
        const val ANIMATION_DURATION = 500L
        const val FISH_TRANSLATION = 100F
        const val FISH_ROTATION = 60F
        const val TEXT_MIN_ALPHA = 0.3F
        const val TEXT_MAX_ALPH = 0.9F
        const val SPLASH_WAIT_TIME = 5000L
    }
}
