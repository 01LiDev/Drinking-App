package com.example.drinkinggame


import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.drinkinggame.databinding.ActivityCreateGameBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

var timer = 20
var maxPlayer = 2
var host = ""

class CreateGame : AppCompatActivity() {
    private lateinit var hostQuestions: DocumentReference
    private lateinit var binding: ActivityCreateGameBinding
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore
    private var playerIcon = ""
    private var playerName = ""
    private var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        auth = FirebaseAuth.getInstance()
        host = auth.currentUser?.uid.toString()
        val qSetNamesref = db.collection("Account Data")
            .document(auth.currentUser?.uid.toString())
            .collection("Question Set Name Edit")

        qSetNamesref.document("Names").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("Main", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                Log.d("Main", "Current data: ${snapshot.data}")
                when (questionsetselected) {
                    1 -> {
                        binding.setSelected.text = snapshot.getString("QS1Name").toString()
                    }
                    2 -> {
                        binding.setSelected.text = snapshot.getString("QS2Name").toString()
                    }
                    3 -> {
                        binding.setSelected.text = snapshot.getString("QS3Name").toString()
                    }
                }

            } else {
                Log.d("Main", "Current data: null")
            }
        }
        val playerInfo = db.collection("Account Data").document(auth.currentUser!!.uid)
        playerInfo.get().addOnSuccessListener { document ->
            playerName = document.getString("Display Name").toString()
            playerIcon = document.getString("Icon").toString()
        }
        binding.playerError.visibility = View.INVISIBLE
        binding.timerError.visibility = View.INVISIBLE
        binding.roomcodeError.visibility = View.INVISIBLE
        binding.roomcodeError2.visibility = View.INVISIBLE
        binding.create.setOnClickListener {
            validSettings()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
    private fun doesRoomCodeExists() {
        auth = FirebaseAuth.getInstance()
        val checkRoom = db.collection("Rooms").document()
        hostQuestions = when (questionsetselected) {
            3 -> {
                db.collection("Account Data").document(auth.currentUser!!.uid)
                    .collection("Question Sets").document("Set3")
            }
            2 -> {
                db.collection("Account Data").document(auth.currentUser!!.uid)
                    .collection("Question Sets").document("Set2")
            }
            else -> {
                db.collection("Account Data").document(auth.currentUser!!.uid)
                    .collection("Question Sets").document("Set1")
            }
        }
        var q1: String
        var q2: String
        var q3: String
        var q4: String
        var q5: String
        var q6: String
        var q7: String
        var q8: String
        var q9: String
        var q10: String
        var q11: String
        var q12: String
        var q13: String
        var q14: String
        var q15: String
        var q16: String
        var q17: String
        var q18: String
        var q19: String
        var q20: String

        checkRoom.addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, e ->
            if (e != null) {
                Log.w("Main", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.exists()) {
                Log.d("Main", "making room")
                val makeRoom =
                    db.collection("Rooms").document(binding.roomcodeInput.text.toString())
                val playersInRoom = hashMapOf(
                    "Player 1" to playerName,
                    "Player 2" to "",
                    "Player 3" to "",
                    "Player 4" to "",
                    "Player 5" to "",
                    "Player 6" to "",
                    "Player 1 Icon" to playerIcon,
                    "Player 2 Icon" to "",
                    "Player 3 Icon" to "",
                    "Player 4 Icon" to "",
                    "Player 5 Icon" to "",
                    "Player 6 Icon" to "",
                    "Player 1 Counter" to 0,
                    "Player 2 Counter" to 0,
                    "Player 3 Counter" to 0,
                    "Player 4 Counter" to 0,
                    "Player 5 Counter" to 0,
                    "Player 6 Counter" to 0,
                )

                hostQuestions.get().addOnSuccessListener { document ->
                    q1 = document.getString("Q1").toString()
                    q2 = document.getString("Q2").toString()
                    q3 = document.getString("Q3").toString()
                    q4 = document.getString("Q4").toString()
                    q5 = document.getString("Q5").toString()
                    q6 = document.getString("Q6").toString()
                    q7 = document.getString("Q7").toString()
                    q8 = document.getString("Q8").toString()
                    q9 = document.getString("Q9").toString()
                    q10 = document.getString("Q10").toString()
                    q11 = document.getString("Q11").toString()
                    q12 = document.getString("Q12").toString()
                    q13 = document.getString("Q13").toString()
                    q14 = document.getString("Q14").toString()
                    q15 = document.getString("Q15").toString()
                    q16 = document.getString("Q16").toString()
                    q17 = document.getString("Q17").toString()
                    q18 = document.getString("Q18").toString()
                    q19 = document.getString("Q19").toString()
                    q20 = document.getString("Q20").toString()
                    val questionSetInUse = hashMapOf(
                        "Q1" to q1,
                        "Q2" to q2,
                        "Q3" to q3,
                        "Q4" to q4,
                        "Q5" to q5,
                        "Q6" to q6,
                        "Q7" to q7,
                        "Q8" to q8,
                        "Q9" to q9,
                        "Q10" to q10,
                        "Q11" to q11,
                        "Q12" to q12,
                        "Q13" to q13,
                        "Q14" to q14,
                        "Q15" to q15,
                        "Q16" to q16,
                        "Q17" to q17,
                        "Q18" to q18,
                        "Q19" to q19,
                        "Q20" to q20

                    )
                    Log.d("Main", q1)
                    makeRoom.collection("Questions").document("Questions to be Used")
                        .set(questionSetInUse)

                }

                val roomSettings = hashMapOf(
                    "Host" to host,
                    "Max Players" to maxPlayer,
                    "Timer" to timer,
                    "Game Status" to "Pause",
                    "Question Turn" to 1,
                    "Player Turn" to host
                )
                makeRoom.set(roomSettings)
                makeRoom.collection("Players").document("PlayersData").set(playersInRoom)
                makeRoom.collection("Questions").document("Questions to be Used")


                binding.roomcodeError2.visibility = View.INVISIBLE
                isHost = true
                currentRoom = binding.roomcodeInput.text.toString()
                val intent = Intent(this, ActiveGame::class.java)
                startActivity(intent)
            } else if (snapshot != null && snapshot.exists()) {
                binding.roomcodeError2.visibility = View.VISIBLE
            }
        }
    }

    private fun validSettings() {
        timer = binding.timer.text.toString().toInt()
        maxPlayer = binding.players.text.toString().toInt()
        var maxPlayerValid = false
        var timerValid = false
        var roomcodeValid = false
        if (maxPlayer < 2 || maxPlayer > 6 || binding.players.text.toString().isEmpty()) {
            binding.playerError.visibility = View.VISIBLE
        } else {
            binding.playerError.visibility = View.INVISIBLE
            maxPlayer = binding.players.text.toString().toInt()
            maxPlayerValid = true
        }

        if (timer < 10 || timer > 99 || binding.timer.text.toString().isEmpty()) {
            binding.timerError.visibility = View.VISIBLE
        } else {
            binding.timerError.visibility = View.INVISIBLE
            timer = binding.timer.text.toString().toInt()
            timerValid = true
        }
        if (binding.roomcodeInput.text.toString().isEmpty()) {
            binding.roomcodeError.visibility = View.VISIBLE
        } else {
            binding.roomcodeError.visibility = View.INVISIBLE
            currentRoom = binding.roomcodeInput.text.toString()
            roomcodeValid = true
        }
        if (maxPlayerValid && timerValid && roomcodeValid) {
            doesRoomCodeExists()
        } else {
            binding.roomcodeError2.visibility = View.INVISIBLE
        }

    }


}
