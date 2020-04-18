package com.example.awesomefish.scene.host

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.awesomefish.menu.PauseMenu
import com.example.awesomefish.shared.SoundManager
import com.example.awesomefish.start.GameLauncher
import java.util.*

class GameHostActivity : AppCompatActivity() {
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

    private fun showMenuDialog() {
        PauseMenu.instance().show(supportFragmentManager, "pause_menu")
    }

    override fun onBackPressed() {
        showMenuDialog()
    }

    override fun onDestroy() {
        super.onDestroy()
        launcher.onDestroy()
    }

    companion object {
        const val LOOP_INTERVAL = (1000 / 60).toLong()
    }
}
