package com.example.drinkinggame

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class FailScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fail_screen)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        isHost = false
        Handler().postDelayed(Runnable {
            val i = Intent(this, InitialScreen::class.java)
            startActivity(i)
        }, 5000)

    }
}