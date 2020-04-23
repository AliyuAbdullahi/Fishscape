package com.example.awesomefish.ui.stages

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.awesomefish.R
import com.example.awesomefish.menu.PauseMenu
import com.example.awesomefish.shared.SoundManager
import com.example.awesomefish.ui.GameLauncher
import java.util.*

class GameHostActivity : AppCompatActivity(), PauseMenu.PauseMenuItemClickedListener {
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
    }

    private fun showMenuDialog() {
        soundManager.playShortSound(SoundManager.ShortSound.REVEAL_TWO, SoundManager.Loop.DONT_LOOP)
        PauseMenu.show(supportFragmentManager)
    }

    private fun hideMenuDialog() {
        soundManager.playShortSound(SoundManager.ShortSound.REVEAL_TWO, SoundManager.Loop.DONT_LOOP)
        PauseMenu.hide(supportFragmentManager)
    }

    override fun onBackPressed() {
        launcher.onPause()
        showMenuDialog()
    }

    override fun onDestroy() {
        super.onDestroy()
        launcher.onDestroy()
    }

    companion object {
        const val LOOP_INTERVAL = (1000 / 60).toLong()
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
        AlertDialog.Builder(this)
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
            }.create().show()
    }
}
