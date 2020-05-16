package com.example.awesomefish.ui.stages

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.awesomefish.R
import com.example.awesomefish.scene.GameOverScene
import com.example.awesomefish.shared.FoodManager
import com.example.awesomefish.shared.SoundManager
import com.example.awesomefish.ui.GameLauncher
import com.example.awesomefish.ui.menu.PAUSE_MENU_TAG
import com.example.awesomefish.ui.menu.PauseMenu
import com.example.awesomefish.ui.start.StartActivity
import java.util.*
import kotlin.system.exitProcess

class GameHostActivity : AppCompatActivity(), PauseMenu.PauseMenuItemClickedListener,
    GameOverScene.GameOverClickListener {
    private lateinit var launcher: GameLauncher
    private lateinit var soundManager: SoundManager

    private var handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        soundManager = SoundManager.instance(this)
        soundManager.stopBackgroundSound()
        Handler().postDelayed({
            soundManager.playLongTrack(SoundManager.BackgroundSound.WELCOME_SCREEN)
        }, 100)
        launcher = GameLauncher(this, soundManager)

        setContentView(launcher)

        val timer = Timer()

        timer.schedule(
            object : TimerTask() {
                override fun run() {
                    handler.post { launcher.invalidate() }
                }
            }, 0,
            LOOP_INTERVAL
        )
    }

    override fun onPause() {
        super.onPause()
        soundManager.pauseBackgroundSound()
    }

    override fun onResume() {
        super.onResume()
        soundManager.resume()
        launcher.onResume()
    }

    private fun showMenuDialog() {
        soundManager.playShortSound(SoundManager.ShortSound.REVEAL_TWO, SoundManager.Loop.DONT_LOOP)
        PauseMenu.show(supportFragmentManager)
    }

    private fun hideMenuDialog() {
        if (supportFragmentManager.findFragmentByTag(PAUSE_MENU_TAG) != null) {
            soundManager.playShortSound(
                SoundManager.ShortSound.REVEAL_TWO,
                SoundManager.Loop.DONT_LOOP
            )
            PauseMenu.hide(supportFragmentManager)
        }
    }

    override fun onBackPressed() {
        launcher.onPause()
        showMenuDialog()
    }

    override fun onDestroy() {
        super.onDestroy()
        launcher.onDestroy()
    }

    override fun resumeClicked() {
        hideMenuDialog()
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
        /*AlertDialog.Builder(this)
            .setTitle(R.string.quit_dialog_title)
            .setMessage(R.string.quit_dialog_message)
            .setPositiveButton(
                getString(R.string.yes)
            ) { _, _ ->
                finish()
            }.setNegativeButton(
                getString(R.string.no)
            ) { _, _ ->
                hideMenuDialog()
            }.create().show()*/

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.quit_game_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.show()
        val positiveBtn:Button = dialog.findViewById(R.id.quit_positive_button)
        val negativeBtn:Button = dialog.findViewById(R.id.quit_negative__button)
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
