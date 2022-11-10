package com.example.drinkinggame

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text
var selectedIcon = ""
class InitialScreen : AppCompatActivity() {
    private lateinit var jGame: Button
    private lateinit var cQuestions: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var bLogout: Button
    private lateinit var userUID: TextView
    private lateinit var cGame: AppCompatButton
    private lateinit var cName: AppCompatButton
    private lateinit var cIcon: AppCompatButton
    private lateinit var displayName: TextView
    private lateinit var userIcon: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initialscreen)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        auth = FirebaseAuth.getInstance()
        jGame = findViewById(R.id.joinGame)
        cQuestions = findViewById(R.id.cQuestionSet)
        bLogout = findViewById(R.id.log_out)
        userUID = findViewById(R.id.userUID)
        cGame = findViewById(R.id.createGame)
        cName = findViewById(R.id.changeNameB)
        cIcon = findViewById(R.id.changeIconB)
        displayName = findViewById(R.id.displayname)
        userIcon = findViewById(R.id.iconimginitial)

        cIcon.setOnClickListener {
            val intent = Intent(this,AvatarIcons::class.java)
            startActivity(intent)
        }
        cName.setOnClickListener { changeName() }
        jGame.setOnClickListener {
            val intent = Intent(this, ActiveGame::class.java)
            startActivity(intent)
        }

        cQuestions.setOnClickListener {

            val intent = Intent(this, QuestionSets::class.java)
            startActivity(intent)
        }
        cGame.setOnClickListener {
            val intent = Intent(this, CreateGame::class.java)
            startActivity(intent)
        }
        bLogout.setOnClickListener { logout() }

    }
    private fun changeName(){
        val input = EditText(this)
        input.hint = "Type Here"
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Change Your Display Name")
            .setView(input)
            .setCancelable(true)
            .setPositiveButton("Confirm",DialogInterface.OnClickListener{dialog, id ->
                displayName.text = input.text.toString()
            }).setNegativeButton("Cancel", DialogInterface.OnClickListener{dialog, id ->
                dialog.cancel()
            })
        val alert = dialogBuilder.create()
        // show alert dialog
        alert.show()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    private fun updateUI(user: FirebaseUser?){
        val currentUserUid = auth.currentUser?.uid.toString()
        userUID.text = currentUserUid
    }
    private fun logout(){
        Firebase.auth.signOut()
        val intent = Intent(this, LoginScreen::class.java)
        startActivity(intent)
    }
}