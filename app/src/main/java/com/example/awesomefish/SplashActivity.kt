package com.example.awesomefish

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startGameScreen()
    }

    private fun startGameScreen() {
        Handler().postDelayed({ startActivity(Intent(this@SplashActivity, MainActivity::class.java))}, 3000)
    }
}
