package com.example.awesomefish.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.example.awesomefish.R

class AboutPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_page)
        setupToolBar()
        val cardview: CardView = findViewById(R.id.about_card_view)
        cardview.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun setupToolBar(){
        setSupportActionBar(findViewById(R.id.about_page_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_forward)
        supportActionBar?.title = ""
    }
}