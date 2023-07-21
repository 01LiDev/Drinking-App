package com.example.drinkinggame

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SafeScreen : AppCompatActivity() {
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_safe_screen)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        val checkPCounter = db.collection("Rooms").document(currentRoom).collection("Players")
            .document("PlayersData")
        checkPCounter.addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                when (playerNumber) {
                    1 -> {
                        checkPCounter.update("Player 1 Counter", 0)
                    }
                    2 -> {
                        checkPCounter.update("Player 2 Counter", 0)

                    }
                    3 -> {
                        checkPCounter.update("Player 3 Counter", 0)

                    }
                    4 -> {
                        checkPCounter.update("Player 4 Counter", 0)

                    }
                    5 -> {
                        checkPCounter.update("Player 5 Counter", 0)

                    }
                    6 -> {
                        checkPCounter.update("Player 6 Counter", 0)
                    }
                }

            }
        }
        Handler().postDelayed(Runnable {
            val i = Intent(this, ActiveGame::class.java)
            startActivity(i)
        }, 5000)
    }
}