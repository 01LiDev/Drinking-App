package com.example.drinkinggame

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        auth = FirebaseAuth.getInstance()

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        val someDelay = Thread {
            Thread.sleep(2300)
            if (auth.currentUser == null) {
                val intent = Intent(this, LoginScreen::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, InitialScreen::class.java)
                startActivity(intent)
                finish()
            }
        }
        someDelay.start()
    }
}