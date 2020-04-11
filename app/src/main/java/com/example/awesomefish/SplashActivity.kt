package com.example.awesomefish

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.awesomefish.shared.FontManager
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashTitle.setTypeface(FontManager.getTypeForFont(this, FontManager.Font.GLADIATOR_SPORT))
        startGameScreen()
    }

    private fun startGameScreen() {
        Handler().postDelayed({
            startActivity(
                Intent(
                    this@SplashActivity,
                    MainActivity::class.java
                )
            )
        }, 5000)
    }
}
