package com.example.drinkinggame


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.drinkinggame.databinding.ActivityCreateGameBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

var timer = 20
var maxPlayer = 2
var hostRoomCode = ""
var host = ""

class CreateGame : AppCompatActivity() {
    private lateinit var hostQuestions: DocumentReference
    private lateinit var binding: ActivityCreateGameBinding
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore
    private var playerIcon = ""
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
        binding.playerError.visibility = View.INVISIBLE
        binding.timerError.visibility = View.INVISIBLE
        binding.roomcodeError.visibility = View.INVISIBLE
        binding.roomcodeError2.visibility = View.INVISIBLE
        binding.create.setOnClickListener {
            validSettings()
        }

    }

    private fun doesRoomCodeExists() {
        auth = FirebaseAuth.getInstance()
        val checkRoom = db.collection("Rooms").document()

        if(questionsetselected == 3){
            hostQuestions = db.collection("Account Data").document(auth.currentUser!!.uid).collection("Question Sets").document("Set3")
        }else if(questionsetselected == 2){
            hostQuestions = db.collection("Account Data").document(auth.currentUser!!.uid).collection("Question Sets").document("Set2")
        }else{
            hostQuestions = db.collection("Account Data").document(auth.currentUser!!.uid).collection("Question Sets").document("Set1")
        }
        var q1 = ""
        var q2 = ""
        var q3 = ""
        var q4 = ""
        var q5 = ""
        var q6 = ""
        var q7 = ""
        var q8 = ""
        var q9 = ""
        var q10 = ""
        var q11 = ""
        var q12 = ""
        var q13 = ""
        var q14 = ""
        var q15 = ""
        var q16 = ""
        var q17 = ""
        var q18 = ""
        var q19 = ""
        var q20 = ""

        checkRoom.addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, e ->
            if (e != null) {
                Log.w("Main", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.exists()) {
                Log.d("Main", "making room")
                val playerInfo = db.collection("Account Data").document(auth.currentUser!!.uid)
                playerInfo.get().addOnSuccessListener { document ->
                     playerIcon = document.getString("Icon").toString()
                }
                val makeRoom =
                    db.collection("Rooms").document(binding.roomcodeInput.text.toString())
                val playersInRoom = hashMapOf(
                    "Player 1" to auth.currentUser!!.uid,
                    "Player 2" to "",
                    "Player 3" to "",
                    "Player 4" to "",
                    "Player 5" to "",
                    "Player 6" to "",
                    "Icon 1" to playerIcon,
                    "Icon 2" to "",
                    "Icon 3" to "",
                    "Icon 4" to "",
                    "Icon 5" to "",
                    "Icon 6" to ""
                    )

                hostQuestions.get().addOnSuccessListener { document->
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
                        makeRoom.collection("Questions").document("Questions to be Used").set(questionSetInUse)

                }

                val roomSettings = hashMapOf(
                    "Host" to host,
                    "Max Players" to maxPlayer,
                    "Timer" to timer,
                    "Current Players" to 1,
                    "Room Status" to "Pause",
                    "Question Turn" to 1,
                    "Player Turn" to host
                )
                makeRoom.set(roomSettings)
                makeRoom.collection("Players").document("Player UIDs").set(playersInRoom)
                makeRoom.collection("Questions").document("Questions to be Used")


                binding.roomcodeError2.visibility = View.INVISIBLE
                isHost = true
                hostRoomCode = binding.roomcodeInput.text.toString()
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
            hostRoomCode = binding.roomcodeInput.text.toString()
            roomcodeValid = true
        }
        if (maxPlayerValid && timerValid && roomcodeValid) {
            doesRoomCodeExists()
        } else {
            binding.roomcodeError2.visibility = View.INVISIBLE
        }

    }


}
