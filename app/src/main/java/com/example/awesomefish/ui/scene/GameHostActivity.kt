package com.example.awesomefish.ui.scene

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.awesomefish.R
import com.example.awesomefish.di.DI
import com.example.awesomefish.domain.data.GameStatus
import com.example.awesomefish.shared.FoodManager
import com.example.awesomefish.shared.SoundManager
import com.example.awesomefish.ui.AboutPage
import com.example.awesomefish.ui.GameLauncher
import com.example.awesomefish.ui.dialogs.PauseMenuDialog
import com.example.awesomefish.ui.gameover.GameOverScene
import com.example.awesomefish.ui.menu.PAUSE_MENU_TAG
import com.example.awesomefish.ui.menu.PauseMenu
import com.example.awesomefish.ui.start.StartActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.system.exitProcess

class GameHostActivity : AppCompatActivity(), PauseMenuDialog.PauseMenuDialogItemClickedListener,
    GameOverScene.GameOverClickListener {
    private lateinit var launcher: GameLauncher
    private lateinit var soundManager: SoundManager
    private val gameStatusDao = DI.provideGameStatusDao()

    private var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        soundManager = SoundManager.instance(this)
        soundManager.stopBackgroundSound()
        gameUnPaused()

        Handler().postDelayed({
            soundManager.playLongTrack(SoundManager.BackgroundSound.WELCOME_SCREEN)
        }, 100)
        launcher = GameLauncher(this, soundManager)

        setContentView(launcher)

        val timer = Timer()

        timer.schedule(
            object : TimerTask() {
                override fun run() {
                    Log.v("GAME STATE", "${GameState.running}")
                    if (GameState.running) {
                        handler.post { launcher.invalidate() }
                    }
                }
            }, 0,
            LOOP_INTERVAL
        )
    }

    override fun onPause() {
        super.onPause()
        soundManager.pauseBackgroundSound()
        saveGamePaused()
    }

    override fun onResume() {
        super.onResume()
        resumeGame()
    }

    private fun resumeGame() {
        GlobalScope.launch {
            if (GameState.dialogVisible) {
                GameState.running = gameStatusDao.getGameStatus().firstOrNull()?.running ?: true
            } else {
                GameState.running = true
            }
            withContext(Dispatchers.Main) {
                soundManager.resume()
                launcher.onResume()
            }
        }
    }

    private fun showMenuDialog() {
        GameState.dialogVisible = true
        soundManager.playShortSound(SoundManager.ShortSound.REVEAL_TWO, SoundManager.Loop.DONT_LOOP)
        PauseMenuDialog.show(supportFragmentManager)
    }

    private fun hideMenuDialog() {
        GameState.dialogVisible = false
        if (supportFragmentManager.findFragmentByTag(PAUSE_MENU_TAG) != null) {
            soundManager.playShortSound(
                SoundManager.ShortSound.REVEAL_TWO,
                SoundManager.Loop.DONT_LOOP
            )
            PauseMenuDialog.hide(supportFragmentManager)
        }
    }

    override fun onBackPressed() {
        GameState.running = false
        launcher.onPause()
        saveGamePaused()
        showMenuDialog()
    }

    override fun onDestroy() {
        super.onDestroy()
        launcher.onDestroy()
        gameUnPaused()
    }

    private fun saveGamePaused() {
        GlobalScope.launch {
            gameStatusDao.saveGameStatus(GameStatus(1, false))
        }
    }

    private fun gameUnPaused() {
        GlobalScope.launch {
            gameStatusDao.saveGameStatus(GameStatus(1, true))
        }
    }

    override fun resumeClicked() {
        hideMenuDialog()
        gameUnPaused()
        GameState.running = true
    }

    override fun aboutClicked() {
        //About page to be implemented
    }

    override fun settingsClicked() {
        //Settings page to be implemented
    }

    override fun quitClicked() {
        showQuitDialog()
    }

    private fun showQuitDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.quit_game_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.show()
        val positiveBtn: Button = dialog.findViewById(R.id.quit_positive_button)
        val negativeBtn: Button = dialog.findViewById(R.id.quit_negative__button)
        positiveBtn.setOnClickListener {
            finish()
            exitProcess(0)
        }
        negativeBtn.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun newGameClicked() {
        FoodManager.clearAll()
        soundManager.stopBackgroundSound()
        soundManager.playLongTrack(SoundManager.BackgroundSound.WELCOME_SCREEN)
        GameLauncher.addScene(GameScene(this, soundManager))
    }

    override fun quitGameClicked() {
        showQuitDialog()
    }

    override fun mainMenuClicked() {
        soundManager.stopBackgroundSound()
        startActivity(Intent(this, StartActivity::class.java))
        finish()
    }

    companion object {
        const val LOOP_INTERVAL = (1000 / 60).toLong()
    }

}
