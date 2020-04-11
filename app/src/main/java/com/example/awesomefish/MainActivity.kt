package com.example.awesomefish

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.awesomefish.game.GameLauncher
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var launcher: GameLauncher
    private var handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launcher = GameLauncher(this)
        setContentView(launcher)

        val timer = Timer()

        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post { launcher.invalidate() }
            }
        }, 0, 30)
    }

}
