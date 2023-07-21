package com.example.drinkinggame

import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
var playOnce = 0
class InitialScreen : AppCompatActivity() {
    private var db = Firebase.firestore
    private lateinit var jGame: Button
    private lateinit var cQuestions: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var bLogout: Button
    private lateinit var cGame: AppCompatButton
    private lateinit var cName: AppCompatButton
    private lateinit var cIcon: AppCompatButton
    private lateinit var displayName: TextView
    private lateinit var userIcon: ImageView
    private lateinit var codeError: TextView
    private lateinit var gameCodeET: EditText
    private lateinit var muteSound: AppCompatButton
    private lateinit var playSound: AppCompatButton

    private var mMediaPlayer: MediaPlayer? = null
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
        cGame = findViewById(R.id.createGame)
        cName = findViewById(R.id.changeNameB)
        cIcon = findViewById(R.id.changeIconB)
        displayName = findViewById(R.id.displayname)
        userIcon = findViewById(R.id.iconimginitial)
        codeError = findViewById(R.id.codeError)
        gameCodeET = findViewById(R.id.gameCode)
        muteSound = findViewById(R.id.mute)
        playSound = findViewById(R.id.playS)
        codeError.visibility = View.INVISIBLE



        updateUI()
        cIcon.setOnClickListener {

            val intent = Intent(this, AvatarIcons::class.java)
            startActivity(intent)
        }
        cName.setOnClickListener { changeName() }
        jGame.setOnClickListener {

            joinGame()
        }

        cQuestions.setOnClickListener {
            playOnce++
            val intent = Intent(this, QuestionSets::class.java)
            startActivity(intent)
        }
        cGame.setOnClickListener {
            playOnce++
            val intent = Intent(this, CreateGame::class.java)
            startActivity(intent)
        }
        bLogout.setOnClickListener {
            logout()
            playOnce++
        }
        if (currentRoom.isNotEmpty()) {
            val deletePrevRoom = db.collection("Rooms").document(currentRoom)
            deletePrevRoom.addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    deletePrevRoom.delete()
                }
            }
        }
        if(mMediaPlayer!=null && mMediaPlayer!!.isPlaying())
        {
            mMediaPlayer!!.stop();
            mMediaPlayer!!.release();
            mMediaPlayer = null;
        }
        playSound()
        muteSound.setOnClickListener {
            if(mMediaPlayer!=null && mMediaPlayer!!.isPlaying())
            {
                mMediaPlayer!!.stop();
                mMediaPlayer!!.release();
                mMediaPlayer = null;
            }
        }
        playSound.setOnClickListener { playSound() }
    }

    private fun playSound() {
        mMediaPlayer = MediaPlayer.create(this, R.raw.lobbymusic)
        mMediaPlayer!!.isLooping = true
        mMediaPlayer!!.start()

    }

    //stops sound


    private fun joinGame() {
        if (gameCodeET.text.isEmpty()) {
            Toast.makeText(applicationContext, "Please Enter a Code!", Toast.LENGTH_SHORT).show()
        } else {
            val checkRoom = db.collection("Rooms").document(gameCodeET.text.toString())
            checkRoom.addSnapshotListener { snapshot, _ ->
                if (snapshot != null && snapshot.exists()) {
                    JoinRoomCode = gameCodeET.text.toString()
                    var maxPlayer = " "
                    val checkMax = db.collection("Rooms").document(JoinRoomCode)
                    checkMax.get().addOnSuccessListener { document ->
                        maxPlayer = document.get("Max Players").toString()
                    }
                    var isFull = true
                    val checkFull =
                        db.collection("Rooms").document(JoinRoomCode).collection("Players")
                            .document("PlayersData")
                    checkFull.get().addOnSuccessListener { document ->
                        val p1name = document.getString("Player 1")
                        val p2name = document.getString("Player 2")
                        val p3name = document.getString("Player 3")
                        val p4name = document.getString("Player 4")
                        val p5name = document.getString("Player 5")
                        val p6name = document.getString("Player 6")
                        if ((p6name == "" || p6name == displayName.text) && maxPlayer == "6") {

                            val intent = Intent(this, ActiveGame::class.java)
                            startActivity(intent)
                        } else if ((p5name == "" || p5name == displayName.text) && maxPlayer == "5") {

                            val intent = Intent(this, ActiveGame::class.java)
                            startActivity(intent)
                        } else if ((p4name == "" || p4name == displayName.text) && maxPlayer == "4") {

                            val intent = Intent(this, ActiveGame::class.java)
                            startActivity(intent)
                        } else if ((p3name == "" || p3name == displayName.text) && maxPlayer == "3") {

                            val intent = Intent(this, ActiveGame::class.java)
                            startActivity(intent)
                        } else if ((p2name == "" || p2name == displayName.text) && maxPlayer == "2") {

                            val intent = Intent(this, ActiveGame::class.java)
                            startActivity(intent)
                        } else if (p1name == displayName.text) {

                            isHost = true
                            currentRoom = JoinRoomCode
                            val intent = Intent(this, ActiveGame::class.java)
                            startActivity(intent)
                        } else {
                            isFull = true
                        }
                        playOnce++
                    }
                    if (isFull) {
                        Toast.makeText(applicationContext, "Room is Full!", Toast.LENGTH_SHORT)
                            .show()
                    }

                } else {
                    Toast.makeText(applicationContext, "Room Does Not Exist", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }
    }

    private fun updateUI() {
        auth = FirebaseAuth.getInstance()
        var savedIcon = ""
        val userProfile = db.collection("Account Data").document(auth.currentUser?.uid.toString())
        userProfile.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("Main", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                Log.d("Main", "Current data: ${snapshot.data}")
                displayName.text = snapshot.getString("Display Name").toString()
                savedIcon = snapshot.getString("Icon").toString()
                userIcon.setImageResource(
                    resources.getIdentifier(
                        savedIcon,
                        "drawable",
                        packageName
                    )
                )
            } else {
                Log.d("Main", "Current data: null")
            }
        }
    }

    private fun updateDisplayName() {
        auth = FirebaseAuth.getInstance()
        val setDisplayName =
            db.collection("Account Data").document(auth.currentUser?.uid.toString())
        setDisplayName.update("Display Name", displayName.text.toString())
    }

    private fun changeName() {
        val input = EditText(this)
        input.hint = "Type Here"
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Change Your Display Name")
            .setView(input)
            .setCancelable(true)
            .setPositiveButton("Confirm", DialogInterface.OnClickListener { _, _ ->
                displayName.text = input.text.toString()
                updateDisplayName()
            }).setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                dialog.cancel()
            })
        val alert = dialogBuilder.create()
        // show alert dialog
        alert.show()
    }

    private fun logout() {
        Firebase.auth.signOut()
        val intent = Intent(this, LoginScreen::class.java)
        startActivity(intent)
    }
}