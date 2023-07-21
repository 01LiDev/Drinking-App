package com.example.drinkinggame

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.drinkinggame.databinding.ActivityActiveGameBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

var JoinRoomCode = ""
var isHost = false
var currentRoom = ""
var playerNumber = 1

class ActiveGame : AppCompatActivity() {
    private lateinit var binding: ActivityActiveGameBinding
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore
    private var playerName = ""
    private var playerIcon = ""
    private var questionCounter = 1
    private var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActiveGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        auth = FirebaseAuth.getInstance()
        val playerInfo = db.collection("Account Data").document(auth.currentUser!!.uid)
        playerInfo.get().addOnSuccessListener { document ->
            playerName = document.getString("Display Name").toString()
            playerIcon = document.getString("Icon").toString()
        }
        invisible()

        if (!isHost) {
            playerJoin()
            updateUIPlayer()
            binding.startBtn.visibility = View.INVISIBLE
            binding.endBtn.visibility = View.INVISIBLE
        } else {
            hostJoin()
            updateUIPlayer()
        }

        binding.startBtn.setOnClickListener {
            val timerSetting = db.collection("Rooms").document(currentRoom)
            timerSetting.update("Game Status", "Started")
        }
        binding.endBtn.setOnClickListener {
            deleteRoom()
        }
        val checkTimer = db.collection("Rooms").document(currentRoom)
        checkTimer.addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                if (snapshot.getString("Game Status") == "Started") {
                    timerStart()
                }
            }
        }
        playerChoice()
        checkDrunk()
        val checkGameEnded =  db.collection("Rooms").document(currentRoom)
        checkGameEnded.addSnapshotListener { snapshot, _ ->
            if (snapshot != null){
                if (snapshot.getString("Game Status") == "Ended"){
                    Toast.makeText(applicationContext, "Host has Closed The Room", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, InitialScreen::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun updateUIPlayer() {
        val playerInfo = db.collection("Account Data").document(auth.currentUser!!.uid)
        playerInfo.get().addOnSuccessListener { document ->
            playerName = document.getString("Display Name").toString()
            playerIcon = document.getString("Icon").toString()
        }
        val playersInRoom = db.collection("Rooms").document(currentRoom).collection("Players")
            .document("PlayersData")
        playersInRoom.addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                val p1name = snapshot.getString("Player 1")
                val p2name = snapshot.getString("Player 2")
                val p3name = snapshot.getString("Player 3")
                val p4name = snapshot.getString("Player 4")
                val p5name = snapshot.getString("Player 5")
                val p6name = snapshot.getString("Player 6")
                val p1icon = snapshot.getString("Player 1 Icon")
                val p2icon = snapshot.getString("Player 2 Icon")
                val p3icon = snapshot.getString("Player 3 Icon")
                val p4icon = snapshot.getString("Player 4 Icon")
                val p5icon = snapshot.getString("Player 5 Icon")
                val p6icon = snapshot.getString("Player 6 Icon")
                binding.P1name.text = p1name
                binding.P2name.text = p2name
                binding.P3name.text = p3name
                binding.P4name.text = p4name
                binding.P5name.text = p5name
                binding.P6name.text = p6name
                binding.P1icon.setImageResource(
                    resources.getIdentifier(
                        p1icon,
                        "drawable",
                        packageName
                    )
                )
                binding.P2icon.setImageResource(
                    resources.getIdentifier(
                        p2icon,
                        "drawable",
                        packageName
                    )
                )
                binding.P3icon.setImageResource(
                    resources.getIdentifier(
                        p3icon,
                        "drawable",
                        packageName
                    )
                )
                binding.P4icon.setImageResource(
                    resources.getIdentifier(
                        p4icon,
                        "drawable",
                        packageName
                    )
                )
                binding.P5icon.setImageResource(
                    resources.getIdentifier(
                        p5icon,
                        "drawable",
                        packageName
                    )
                )
                binding.P6icon.setImageResource(
                    resources.getIdentifier(
                        p6icon,
                        "drawable",
                        packageName
                    )
                )
                if (p6name == "") {
                    visible(5)
                } else if (p5name === "") {
                    visible(4)
                } else if (p4name == "") {
                    visible(3)
                } else if (p3name == "") {
                    visible(2)
                }
                val initQuestion =
                    db.collection("Rooms").document(currentRoom).collection("Questions")
                        .document("Questions to be Used")
                initQuestion.get().addOnSuccessListener { _ ->
                    gameLogic()
                }
                if (isHost || p1name == playerName) {
                    binding.startBtn.visibility = View.VISIBLE
                    binding.endBtn.visibility = View.VISIBLE
                }
                Log.d("Main", "updateUIPlayer: making buttons visible to host")
            }
        }
        val timerSet = db.collection("Rooms").document(currentRoom)
        timerSet.addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                binding.timer.text = snapshot.get("Timer").toString()
            }
        }
        Log.d("Main", "updateUIPlayer: done running")

    }

    private fun timerStart() {
        val timerSetting = db.collection("Rooms").document(currentRoom)
        timerSetting.addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                var timerSet = snapshot.get("Timer").toString().toInt()
                object : CountDownTimer(timerSet.toLong() * 1000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        binding.timer.text = timerSet.toString()
                        timerSet--
                    }

                    override fun onFinish() {
                        gameLogic()
                        binding.iHaveBtn.visibility = View.VISIBLE
                        binding.haveNotBtn.visibility = View.VISIBLE
                        timerStart()
                    }
                }.start()
            }else{
                Toast.makeText(applicationContext, "Host Closed The Room!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, InitialScreen::class.java)
                startActivity(intent)
            }
        }
    }

    private fun checkDrunk() {
        var pCounter = 0
        val checkPCounter = db.collection("Rooms").document(currentRoom).collection("Players")
            .document("PlayersData")
        checkPCounter.addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                when (playerNumber) {
                    1 -> {
                        pCounter = snapshot.get("Player 1 Counter").toString().toInt()
                        if (pCounter == 3) {
                            val intent = Intent(this, CameraMiniGame::class.java)
                            startActivity(intent)
                        }

                    }
                    2 -> {
                        pCounter = snapshot.get("Player 2 Counter").toString().toInt()
                        if (pCounter == 3) {
                            val intent = Intent(this, CameraMiniGame::class.java)
                            startActivity(intent)
                        }

                    }
                    3 -> {
                        pCounter = snapshot.get("Player 3 Counter").toString().toInt()
                        if (pCounter == 3) {
                            val intent = Intent(this, CameraMiniGame::class.java)
                            startActivity(intent)
                        }

                    }
                    4 -> {
                        pCounter = snapshot.get("Player 4 Counter").toString().toInt()
                        if (pCounter == 3) {
                            val intent = Intent(this, CameraMiniGame::class.java)
                            startActivity(intent)
                        }

                    }
                    5 -> {
                        pCounter = snapshot.get("Player 5 Counter").toString().toInt()
                        if (pCounter == 3) {
                            val intent = Intent(this, CameraMiniGame::class.java)
                            startActivity(intent)
                        }

                    }
                    6 -> {
                        pCounter = snapshot.get("Player 6 Counter").toString().toInt()
                        val intent = Intent(this, CameraMiniGame::class.java)
                        startActivity(intent)
                    }
                }

            }
        }
    }

    private fun deleteRoom() {
       val gameEnd =  db.collection("Rooms").document(currentRoom)
        gameEnd.update("Game Status", "Ended")
        val intent = Intent(this, InitialScreen::class.java)
        startActivity(intent)
    }

    private fun playerChoice() {
        var pCounter = 0
        val checkPCounter = db.collection("Rooms").document(currentRoom).collection("Players")
            .document("PlayersData")
        checkPCounter.addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                when (playerNumber) {
                    1 -> {
                        binding.iHaveBtn.setOnClickListener {
                            pCounter = snapshot.get("Player 1 Counter").toString().toInt()
                            pCounter++
                            checkPCounter.update("Player 1 Counter", pCounter)
                            binding.iHaveBtn.visibility = View.INVISIBLE
                            binding.haveNotBtn.visibility = View.INVISIBLE


                        }
                        binding.haveNotBtn.setOnClickListener {
                            binding.iHaveBtn.visibility = View.INVISIBLE
                            binding.haveNotBtn.visibility = View.INVISIBLE

                        }
                    }
                    2 -> {
                        binding.iHaveBtn.setOnClickListener {
                            pCounter = snapshot.get("Player 2 Counter").toString().toInt()
                            pCounter++
                            checkPCounter.update("Player 2 Counter", pCounter)
                            binding.iHaveBtn.visibility = View.INVISIBLE
                            binding.haveNotBtn.visibility = View.INVISIBLE


                        }
                        binding.haveNotBtn.setOnClickListener {
                            binding.iHaveBtn.visibility = View.INVISIBLE
                            binding.haveNotBtn.visibility = View.INVISIBLE

                        }
                    }
                    3 -> {
                        binding.iHaveBtn.setOnClickListener {
                            pCounter = snapshot.get("Player 3 Counter").toString().toInt()
                            pCounter++
                            checkPCounter.update("Player 3 Counter", pCounter)
                            binding.iHaveBtn.visibility = View.INVISIBLE
                            binding.haveNotBtn.visibility = View.INVISIBLE


                        }
                        binding.haveNotBtn.setOnClickListener {
                            binding.iHaveBtn.visibility = View.INVISIBLE
                            binding.haveNotBtn.visibility = View.INVISIBLE

                        }
                    }
                    4 -> {
                        binding.iHaveBtn.setOnClickListener {
                            pCounter = snapshot.get("Player 4 Counter").toString().toInt()
                            pCounter++
                            checkPCounter.update("Player 4 Counter", pCounter)
                            binding.iHaveBtn.visibility = View.INVISIBLE
                            binding.haveNotBtn.visibility = View.INVISIBLE


                        }
                        binding.haveNotBtn.setOnClickListener {
                            binding.iHaveBtn.visibility = View.INVISIBLE
                            binding.haveNotBtn.visibility = View.INVISIBLE

                        }
                    }
                    5 -> {
                        binding.iHaveBtn.setOnClickListener {
                            pCounter = snapshot.get("Player 5 Counter").toString().toInt()
                            pCounter++
                            checkPCounter.update("Player 5 Counter", pCounter)
                            binding.iHaveBtn.visibility = View.INVISIBLE
                            binding.haveNotBtn.visibility = View.INVISIBLE


                        }
                        binding.haveNotBtn.setOnClickListener {
                            binding.iHaveBtn.visibility = View.INVISIBLE
                            binding.haveNotBtn.visibility = View.INVISIBLE

                        }
                    }
                    6 -> {
                        binding.iHaveBtn.setOnClickListener {
                            pCounter = snapshot.get("Player 6 Counter").toString().toInt()
                            pCounter++
                            checkPCounter.update("Player 6 Counter", pCounter)
                            binding.iHaveBtn.visibility = View.INVISIBLE
                            binding.haveNotBtn.visibility = View.INVISIBLE


                        }
                        binding.haveNotBtn.setOnClickListener {
                            binding.iHaveBtn.visibility = View.INVISIBLE
                            binding.haveNotBtn.visibility = View.INVISIBLE

                        }
                    }
                }
            }
        }
    }

    private fun gameLogic() {
        questionCounter++

        val changeQuestions = db.collection("Rooms").document(currentRoom).collection("Questions")
            .document("Questions to be Used")
        changeQuestions.get().addOnSuccessListener { document ->
            when (questionCounter) {
                1 -> {
                    binding.Question.text = document.getString("Q1")
                    questionCounter++
                }
                2 -> {
                    binding.Question.text = document.getString("Q2")
                    questionCounter++
                }
                3 -> {
                    binding.Question.text = document.getString("Q3")
                    questionCounter++
                }
                4 -> {
                    binding.Question.text = document.getString("Q4")
                    questionCounter++
                }
                5 -> {
                    binding.Question.text = document.getString("Q5")
                    questionCounter++
                }
                6 -> {
                    binding.Question.text = document.getString("Q6")
                    questionCounter++
                }
                7 -> {
                    binding.Question.text = document.getString("Q7")
                    questionCounter++
                }
                8 -> {
                    binding.Question.text = document.getString("Q8")
                    questionCounter++
                }
                9 -> {
                    binding.Question.text = document.getString("Q9")
                    questionCounter++
                }
                10 -> {
                    binding.Question.text = document.getString("Q10")
                    questionCounter++
                }
                11 -> {
                    binding.Question.text = document.getString("Q11")
                    questionCounter++
                }
                12 -> {
                    binding.Question.text = document.getString("Q12")
                    questionCounter++
                }
                13 -> {
                    binding.Question.text = document.getString("Q13")
                    questionCounter++
                }
                14 -> {
                    binding.Question.text = document.getString("Q14")
                    questionCounter++
                }
                15 -> {
                    binding.Question.text = document.getString("Q15")
                    questionCounter++
                }
                16 -> {
                    binding.Question.text = document.getString("Q16")
                    questionCounter++
                }
                17 -> {
                    binding.Question.text = document.getString("Q17")
                    questionCounter++
                }
                18 -> {
                    binding.Question.text = document.getString("Q18")
                    questionCounter++
                }
                19 -> {
                    binding.Question.text = document.getString("Q19")
                    questionCounter++
                }
                20 -> {
                    binding.Question.text = document.getString("Q20")
                    questionCounter++
                }
                else -> {
                    binding.Question.text = "No More Questions!"
                }
            }
        }.addOnFailureListener {
            val intent = Intent(this, InitialScreen::class.java)
            startActivity(intent)
        }
    }

    private fun invisible() {
        binding.P1name.visibility = View.INVISIBLE
        binding.P2name.visibility = View.INVISIBLE
        binding.P3name.visibility = View.INVISIBLE
        binding.P4name.visibility = View.INVISIBLE
        binding.P5name.visibility = View.INVISIBLE
        binding.P6name.visibility = View.INVISIBLE
        binding.P1icon.visibility = View.INVISIBLE
        binding.P2icon.visibility = View.INVISIBLE
        binding.P3icon.visibility = View.INVISIBLE
        binding.P4icon.visibility = View.INVISIBLE
        binding.P5icon.visibility = View.INVISIBLE
        binding.P6icon.visibility = View.INVISIBLE
    }

    private fun visible(x: Int) {
        Log.d("Main", "visible: is working")
        when (x) {
            1 -> {
                binding.P1name.visibility = View.VISIBLE
                binding.P1icon.visibility = View.VISIBLE
                binding.startBtn.visibility = View.VISIBLE
                binding.endBtn.visibility = View.VISIBLE
            }
            2 -> {
                binding.P1name.visibility = View.VISIBLE
                binding.P1icon.visibility = View.VISIBLE
                binding.P2name.visibility = View.VISIBLE
                binding.P2icon.visibility = View.VISIBLE

            }
            3 -> {
                binding.P1name.visibility = View.VISIBLE
                binding.P1icon.visibility = View.VISIBLE
                binding.P2name.visibility = View.VISIBLE
                binding.P2icon.visibility = View.VISIBLE
                binding.P3name.visibility = View.VISIBLE
                binding.P3icon.visibility = View.VISIBLE

            }
            4 -> {
                binding.P1name.visibility = View.VISIBLE
                binding.P1icon.visibility = View.VISIBLE
                binding.P2name.visibility = View.VISIBLE
                binding.P2icon.visibility = View.VISIBLE
                binding.P3name.visibility = View.VISIBLE
                binding.P3icon.visibility = View.VISIBLE
                binding.P4name.visibility = View.VISIBLE
                binding.P4icon.visibility = View.VISIBLE

            }
            5 -> {
                binding.P1name.visibility = View.VISIBLE
                binding.P1icon.visibility = View.VISIBLE
                binding.P2name.visibility = View.VISIBLE
                binding.P2icon.visibility = View.VISIBLE
                binding.P3name.visibility = View.VISIBLE
                binding.P3icon.visibility = View.VISIBLE
                binding.P4name.visibility = View.VISIBLE
                binding.P4icon.visibility = View.VISIBLE
                binding.P5name.visibility = View.VISIBLE
                binding.P5icon.visibility = View.VISIBLE

            }
            6 -> {
                binding.P1name.visibility = View.VISIBLE
                binding.P1icon.visibility = View.VISIBLE
                binding.P2name.visibility = View.VISIBLE
                binding.P2icon.visibility = View.VISIBLE
                binding.P3name.visibility = View.VISIBLE
                binding.P3icon.visibility = View.VISIBLE
                binding.P4name.visibility = View.VISIBLE
                binding.P4icon.visibility = View.VISIBLE
                binding.P5name.visibility = View.VISIBLE
                binding.P5icon.visibility = View.VISIBLE
                binding.P6name.visibility = View.VISIBLE
                binding.P6icon.visibility = View.VISIBLE

            }
        }

    }

    private fun hostJoin() {

        val addHostToRoom = db.collection("Rooms").document(currentRoom).collection("Players")
            .document("PlayersData")
        binding.roomcode.append(currentRoom)
        addHostToRoom.get().addOnSuccessListener { document ->

            binding.P1name.text = document.getString("Player 1")
            val p1Icon = document.getString("Player 1 Icon")
            binding.P1icon.setImageResource(
                resources.getIdentifier(
                    p1Icon,
                    "drawable",
                    packageName
                )
            )
            visible(1)

        }
        isHost = true
        playerNumber = 1
    }

    private fun playerJoin() {
        val playerJoin = db.collection("Rooms").document(JoinRoomCode).collection("Players")
            .document("PlayersData")
        binding.roomcode.append(JoinRoomCode)
        currentRoom = JoinRoomCode
        playerJoin.get().addOnSuccessListener { document ->
            val p1name = document.getString("Player 1")
            val p2name = document.getString("Player 2")
            val p3name = document.getString("Player 3")
            val p4name = document.getString("Player 4")
            val p5name = document.getString("Player 5")
            val p6name = document.getString("Player 6")

            if (p1name == "" || p1name == playerName) {
                binding.P1name.text = playerName
                binding.P1icon.setImageResource(
                    resources.getIdentifier(
                        playerIcon,
                        "drawable",
                        packageName
                    )
                )
                visible(1)
                val playerData = hashMapOf(
                    "Player 1" to playerName,
                    "Player 1 Icon" to playerIcon
                )
                isHost = true
                playerNumber = 1
                playerJoin.update(playerData as Map<String, String>)
            } else if (p2name == "" || p2name == playerName) {
                binding.P2name.text = playerName
                binding.P2icon.setImageResource(
                    resources.getIdentifier(
                        playerIcon,
                        "drawable",
                        packageName
                    )
                )
                visible(2)
                val playerData = hashMapOf(
                    "Player 2" to playerName,
                    "Player 2 Icon" to playerIcon
                )
                playerNumber = 2
                playerJoin.update(playerData as Map<String, String>)
            } else if (p3name == "" || p3name == playerName) {
                binding.P3name.text = playerName
                binding.P3icon.setImageResource(
                    resources.getIdentifier(
                        playerIcon,
                        "drawable",
                        packageName
                    )
                )
                visible(3)
                val playerData = hashMapOf(
                    "Player 3" to playerName,
                    "Player 3 Icon" to playerIcon
                )
                playerNumber = 3
                playerJoin.update(playerData as Map<String, String>)
            } else if (p4name == "" || p4name == playerName) {
                binding.P4name.text = playerName
                binding.P4icon.setImageResource(
                    resources.getIdentifier(
                        playerIcon,
                        "drawable",
                        packageName
                    )
                )
                visible(4)
                val playerData = hashMapOf(
                    "Player 4" to playerName,
                    "Player 4 Icon" to playerIcon
                )
                playerNumber = 4
                playerJoin.update(playerData as Map<String, String>)
            } else if (p5name == "" || p5name == playerName) {
                binding.P5name.text = playerName
                binding.P5icon.setImageResource(
                    resources.getIdentifier(
                        playerIcon,
                        "drawable",
                        packageName
                    )
                )
                visible(5)
                val playerData = hashMapOf(
                    "Player 5" to playerName,
                    "Player 5 Icon" to playerIcon
                )
                playerNumber = 5
                playerJoin.update(playerData as Map<String, String>)
            } else if (p6name == "" || p6name == playerName) {
                binding.P6name.text = playerName
                binding.P6icon.setImageResource(
                    resources.getIdentifier(
                        playerIcon,
                        "drawable",
                        packageName
                    )
                )
                visible(6)
                val playerData = hashMapOf(
                    "Player 2" to playerName,
                    "Player 2 Icon" to playerIcon
                )
                playerNumber = 6
                playerJoin.update(playerData as Map<String, String>)
            }
        }
    }
}
