package com.example.drinkinggame

import android.app.AlertDialog
import android.content.DialogInterface
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.drinkinggame.databinding.ActivityQuestionListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

var questionToEdit = 0
var questionToClear = 0

class QuestionList : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore
    private lateinit var binding: ActivityQuestionListBinding
    private var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        Log.d("Main", "in Question list")
        //Change Questions to saved questions
        updateSaved()

        //Edit buttons
        binding.e1.setOnClickListener {
            questionToEdit = 1
            editQuestion()
        }
        binding.e2.setOnClickListener {
            questionToEdit = 2
            editQuestion()
        }
        binding.e3.setOnClickListener {
            questionToEdit = 3
            editQuestion()
        }
        binding.e4.setOnClickListener {
            questionToEdit = 4
            editQuestion()
        }
        binding.e5.setOnClickListener {
            questionToEdit = 5
            editQuestion()
        }
        binding.e6.setOnClickListener {
            questionToEdit = 6
            editQuestion()
        }
        binding.e7.setOnClickListener {
            questionToEdit = 7
            editQuestion()
        }
        binding.e8.setOnClickListener {
            questionToEdit = 8
            editQuestion()
        }
        binding.e9.setOnClickListener {
            questionToEdit = 9
            editQuestion()
        }
        binding.e10.setOnClickListener {
            questionToEdit = 10
            editQuestion()
        }
        binding.e11.setOnClickListener {
            questionToEdit = 11
            editQuestion()
        }
        binding.e12.setOnClickListener {
            questionToEdit = 12
            editQuestion()
        }
        binding.e13.setOnClickListener {
            questionToEdit = 13
            editQuestion()
        }
        binding.e14.setOnClickListener {
            questionToEdit = 14
            editQuestion()
        }
        binding.e15.setOnClickListener {
            questionToEdit = 15
            editQuestion()
        }
        binding.e16.setOnClickListener {
            questionToEdit = 16
            editQuestion()
        }
        binding.e17.setOnClickListener {
            questionToEdit = 17
            editQuestion()
        }
        binding.e18.setOnClickListener {
            questionToEdit = 18
            editQuestion()
        }
        binding.e19.setOnClickListener {
            questionToEdit = 19
            editQuestion()
        }
        binding.e20.setOnClickListener {
            questionToEdit = 20
            editQuestion()
        }

        //Clear Buttons
        binding.c1.setOnClickListener {
            questionToClear = 1
            clearQuestion()
        }
        binding.c2.setOnClickListener {
            questionToClear = 2
            clearQuestion()
        }
        binding.c3.setOnClickListener {
            questionToClear = 3
            clearQuestion()
        }
        binding.c4.setOnClickListener {
            questionToClear = 4
            clearQuestion()
        }
        binding.c5.setOnClickListener {
            questionToClear = 5
            clearQuestion()
        }
        binding.c6.setOnClickListener {
            questionToClear = 6
            clearQuestion()
        }
        binding.c7.setOnClickListener {
            questionToClear = 7
            clearQuestion()
        }
        binding.c8.setOnClickListener {
            questionToClear = 8
            clearQuestion()
        }
        binding.c9.setOnClickListener {
            questionToClear = 9
            clearQuestion()
        }
        binding.c10.setOnClickListener {
            questionToClear = 10
            clearQuestion()
        }
        binding.c11.setOnClickListener {
            questionToClear = 11
            clearQuestion()
        }
        binding.c12.setOnClickListener {
            questionToClear = 12
            clearQuestion()
        }
        binding.c13.setOnClickListener {
            questionToClear = 13
            clearQuestion()
        }
        binding.c14.setOnClickListener {
            questionToClear = 14
            clearQuestion()
        }
        binding.c15.setOnClickListener {
            questionToClear = 15
            clearQuestion()
        }
        binding.c16.setOnClickListener {
            questionToClear = 16
            clearQuestion()
        }
        binding.c17.setOnClickListener {
            questionToClear = 17
            clearQuestion()
        }
        binding.c18.setOnClickListener {
            questionToClear = 18
            clearQuestion()
        }
        binding.c19.setOnClickListener {
            questionToClear = 19
            clearQuestion()
        }
        binding.c20.setOnClickListener {
            questionToClear = 20
            clearQuestion()
        }

    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
    private fun clearQuestion() {
        auth = FirebaseAuth.getInstance()
        val qSetNamesref =
            db.collection("Account Data").document(auth.currentUser?.uid.toString()).collection(
                "Question Sets"
            )
        when (questionsetedit) {
            1 -> {
                val editSet1 = qSetNamesref.document("Set1")
                when (questionToClear) {
                    1 -> {
                        editSet1.update("Q1", "")
                    }
                    2 -> {
                        editSet1.update("Q2", "")
                    }
                    3 -> {
                        editSet1.update("Q3", "")
                    }
                    4 -> {
                        editSet1.update("Q4", "")
                    }
                    5 -> {
                        editSet1.update("Q5", "")
                    }
                    6 -> {
                        editSet1.update("Q6", "")
                    }
                    7 -> {
                        editSet1.update("Q7", "")
                    }
                    8 -> {
                        editSet1.update("Q8", "")
                    }
                    9 -> {
                        editSet1.update("Q9", "")
                    }
                    10 -> {
                        editSet1.update("Q10", "")
                    }
                    11 -> {
                        editSet1.update("Q11", "")
                    }
                    12 -> {
                        editSet1.update("Q12", "")
                    }
                    13 -> {
                        editSet1.update("Q13", "")
                    }
                    14 -> {
                        editSet1.update("Q14", "")
                    }
                    15 -> {
                        editSet1.update("Q15", "")
                    }
                    16 -> {
                        editSet1.update("Q16", "")
                    }
                    17 -> {
                        editSet1.update("Q17", "")
                    }
                    18 -> {
                        editSet1.update("Q18", "")
                    }
                    19 -> {
                        editSet1.update("Q19", "")
                    }
                    20 -> {
                        editSet1.update("Q20", "")
                    }
                }
            }
            2 -> {
                val editSet2 = qSetNamesref.document("Set2")
                when (questionToClear) {
                    1 -> {
                        editSet2.update("Q1", "")
                    }
                    2 -> {
                        editSet2.update("Q2", "")
                    }
                    3 -> {
                        editSet2.update("Q3", "")
                    }
                    4 -> {
                        editSet2.update("Q4", "")
                    }
                    5 -> {
                        editSet2.update("Q5", "")
                    }
                    6 -> {
                        editSet2.update("Q6", "")
                    }
                    7 -> {
                        editSet2.update("Q7", "")
                    }
                    8 -> {
                        editSet2.update("Q8", "")
                    }
                    9 -> {
                        editSet2.update("Q9", "")
                    }
                    10 -> {
                        editSet2.update("Q10", "")
                    }
                    11 -> {
                        editSet2.update("Q11", "")
                    }
                    12 -> {
                        editSet2.update("Q12", "")
                    }
                    13 -> {
                        editSet2.update("Q13", "")
                    }
                    14 -> {
                        editSet2.update("Q14", "")
                    }
                    15 -> {
                        editSet2.update("Q15", "")
                    }
                    16 -> {
                        editSet2.update("Q16", "")
                    }
                    17 -> {
                        editSet2.update("Q17", "")
                    }
                    18 -> {
                        editSet2.update("Q18", "")
                    }
                    19 -> {
                        editSet2.update("Q19", "")
                    }
                    20 -> {
                        editSet2.update("Q20", "")
                    }
                }
            }
            3 -> {
                val editSet3 = qSetNamesref.document("Set3")
                when (questionToClear) {
                    1 -> {
                        editSet3.update("Q1", "")
                    }
                    2 -> {
                        editSet3.update("Q2", "")
                    }
                    3 -> {
                        editSet3.update("Q3", "")
                    }
                    4 -> {
                        editSet3.update("Q4", "")
                    }
                    5 -> {
                        editSet3.update("Q5", "")
                    }
                    6 -> {
                        editSet3.update("Q6", "")
                    }
                    7 -> {
                        editSet3.update("Q7", "")
                    }
                    8 -> {
                        editSet3.update("Q8", "")
                    }
                    9 -> {
                        editSet3.update("Q9", "")
                    }
                    10 -> {
                        editSet3.update("Q10", "")
                    }
                    11 -> {
                        editSet3.update("Q11", "")
                    }
                    12 -> {
                        editSet3.update("Q12", "")
                    }
                    13 -> {
                        editSet3.update("Q13", "")
                    }
                    14 -> {
                        editSet3.update("Q14", "")
                    }
                    15 -> {
                        editSet3.update("Q15", "")
                    }
                    16 -> {
                        editSet3.update("Q16", "")
                    }
                    17 -> {
                        editSet3.update("Q17", "")
                    }
                    18 -> {
                        editSet3.update("Q18", "")
                    }
                    19 -> {
                        editSet3.update("Q19", "")
                    }
                    20 -> {
                        editSet3.update("Q20", "")
                    }
                }
            }
        }

    }

    private fun updateSaved() {
        auth = FirebaseAuth.getInstance()
        val qSetNamesref =
            db.collection("Account Data").document(auth.currentUser?.uid.toString()).collection(
                "Question Sets"
            )
        when (questionsetedit) {
            1 -> {
                qSetNamesref.document("Set1").addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.w("Main", "Listen failed.", e)
                        return@addSnapshotListener
                    }

                    if (snapshot != null && snapshot.exists()) {
                        Log.d("Main", "Current data: ${snapshot.data}")
                        binding.tv1.text = snapshot.getString("Q1").toString()
                        binding.tv2.text = snapshot.getString("Q2").toString()
                        binding.tv3.text = snapshot.getString("Q3").toString()
                        binding.tv4.text = snapshot.getString("Q4").toString()
                        binding.tv5.text = snapshot.getString("Q5").toString()
                        binding.tv6.text = snapshot.getString("Q6").toString()
                        binding.tv7.text = snapshot.getString("Q7").toString()
                        binding.tv8.text = snapshot.getString("Q8").toString()
                        binding.tv9.text = snapshot.getString("Q9").toString()
                        binding.tv10.text = snapshot.getString("Q10").toString()
                        binding.tv11.text = snapshot.getString("Q11").toString()
                        binding.tv12.text = snapshot.getString("Q12").toString()
                        binding.tv13.text = snapshot.getString("Q13").toString()
                        binding.tv14.text = snapshot.getString("Q14").toString()
                        binding.tv15.text = snapshot.getString("Q15").toString()
                        binding.tv16.text = snapshot.getString("Q16").toString()
                        binding.tv17.text = snapshot.getString("Q17").toString()
                        binding.tv18.text = snapshot.getString("Q18").toString()
                        binding.tv19.text = snapshot.getString("Q19").toString()
                        binding.tv20.text = snapshot.getString("Q20").toString()

                    } else {
                        Log.d("Main", "Current data: null")
                    }
                }
            }
            2 -> {
                qSetNamesref.document("Set2").addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.w("Main", "Listen failed.", e)
                        return@addSnapshotListener
                    }

                    if (snapshot != null && snapshot.exists()) {
                        Log.d("Main", "Current data: ${snapshot.data}")
                        binding.tv1.text = snapshot.getString("Q1").toString()
                        binding.tv2.text = snapshot.getString("Q2").toString()
                        binding.tv3.text = snapshot.getString("Q3").toString()
                        binding.tv4.text = snapshot.getString("Q4").toString()
                        binding.tv5.text = snapshot.getString("Q5").toString()
                        binding.tv6.text = snapshot.getString("Q6").toString()
                        binding.tv7.text = snapshot.getString("Q7").toString()
                        binding.tv8.text = snapshot.getString("Q8").toString()
                        binding.tv9.text = snapshot.getString("Q9").toString()
                        binding.tv10.text = snapshot.getString("Q10").toString()
                        binding.tv11.text = snapshot.getString("Q11").toString()
                        binding.tv12.text = snapshot.getString("Q12").toString()
                        binding.tv13.text = snapshot.getString("Q13").toString()
                        binding.tv14.text = snapshot.getString("Q14").toString()
                        binding.tv15.text = snapshot.getString("Q15").toString()
                        binding.tv16.text = snapshot.getString("Q16").toString()
                        binding.tv17.text = snapshot.getString("Q17").toString()
                        binding.tv18.text = snapshot.getString("Q18").toString()
                        binding.tv19.text = snapshot.getString("Q19").toString()
                        binding.tv20.text = snapshot.getString("Q20").toString()

                    } else {
                        Log.d("Main", "Current data: null")
                    }
                }
            }
            3 -> {
                qSetNamesref.document("Set3").addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.w("Main", "Listen failed.", e)
                        return@addSnapshotListener
                    }

                    if (snapshot != null && snapshot.exists()) {
                        Log.d("Main", "Current data: ${snapshot.data}")
                        binding.tv1.text = snapshot.getString("Q1").toString()
                        binding.tv2.text = snapshot.getString("Q2").toString()
                        binding.tv3.text = snapshot.getString("Q3").toString()
                        binding.tv4.text = snapshot.getString("Q4").toString()
                        binding.tv5.text = snapshot.getString("Q5").toString()
                        binding.tv6.text = snapshot.getString("Q6").toString()
                        binding.tv7.text = snapshot.getString("Q7").toString()
                        binding.tv8.text = snapshot.getString("Q8").toString()
                        binding.tv9.text = snapshot.getString("Q9").toString()
                        binding.tv10.text = snapshot.getString("Q10").toString()
                        binding.tv11.text = snapshot.getString("Q11").toString()
                        binding.tv12.text = snapshot.getString("Q12").toString()
                        binding.tv13.text = snapshot.getString("Q13").toString()
                        binding.tv14.text = snapshot.getString("Q14").toString()
                        binding.tv15.text = snapshot.getString("Q15").toString()
                        binding.tv16.text = snapshot.getString("Q16").toString()
                        binding.tv17.text = snapshot.getString("Q17").toString()
                        binding.tv18.text = snapshot.getString("Q18").toString()
                        binding.tv19.text = snapshot.getString("Q19").toString()
                        binding.tv20.text = snapshot.getString("Q20").toString()

                    } else {
                        Log.d("Main", "Current data: null")
                    }
                }
            }
        }
    }

    private fun updateQuestion() {
        auth = FirebaseAuth.getInstance()
        val qSetNamesref =
            db.collection("Account Data").document(auth.currentUser?.uid.toString()).collection(
                "Question Sets"
            )
        when (questionsetedit) {
            1 -> {
                val qRef = qSetNamesref.document("Set1")
                qRef.update("Q1", binding.tv1.text.toString())
                qRef.update("Q2", binding.tv2.text.toString())
                qRef.update("Q3", binding.tv3.text.toString())
                qRef.update("Q4", binding.tv4.text.toString())
                qRef.update("Q5", binding.tv5.text.toString())
                qRef.update("Q6", binding.tv6.text.toString())
                qRef.update("Q7", binding.tv7.text.toString())
                qRef.update("Q8", binding.tv8.text.toString())
                qRef.update("Q9", binding.tv9.text.toString())
                qRef.update("Q10", binding.tv10.text.toString())
                qRef.update("Q11", binding.tv11.text.toString())
                qRef.update("Q12", binding.tv12.text.toString())
                qRef.update("Q13", binding.tv13.text.toString())
                qRef.update("Q14", binding.tv14.text.toString())
                qRef.update("Q15", binding.tv15.text.toString())
                qRef.update("Q16", binding.tv16.text.toString())
                qRef.update("Q17", binding.tv17.text.toString())
                qRef.update("Q18", binding.tv18.text.toString())
                qRef.update("Q19", binding.tv19.text.toString())
                qRef.update("Q20", binding.tv20.text.toString())
            }
            2 -> {
                val qRef = qSetNamesref.document("Set2")
                qRef.update("Q1", binding.tv1.text.toString())
                qRef.update("Q2", binding.tv2.text.toString())
                qRef.update("Q3", binding.tv3.text.toString())
                qRef.update("Q4", binding.tv4.text.toString())
                qRef.update("Q5", binding.tv5.text.toString())
                qRef.update("Q6", binding.tv6.text.toString())
                qRef.update("Q7", binding.tv7.text.toString())
                qRef.update("Q8", binding.tv8.text.toString())
                qRef.update("Q9", binding.tv9.text.toString())
                qRef.update("Q10", binding.tv10.text.toString())
                qRef.update("Q11", binding.tv11.text.toString())
                qRef.update("Q12", binding.tv12.text.toString())
                qRef.update("Q13", binding.tv13.text.toString())
                qRef.update("Q14", binding.tv14.text.toString())
                qRef.update("Q15", binding.tv15.text.toString())
                qRef.update("Q16", binding.tv16.text.toString())
                qRef.update("Q17", binding.tv17.text.toString())
                qRef.update("Q18", binding.tv18.text.toString())
                qRef.update("Q19", binding.tv19.text.toString())
                qRef.update("Q20", binding.tv20.text.toString())
            }
            3 -> {
                val qRef = qSetNamesref.document("Set3")
                qRef.update("Q1", binding.tv1.text.toString())
                qRef.update("Q2", binding.tv2.text.toString())
                qRef.update("Q3", binding.tv3.text.toString())
                qRef.update("Q4", binding.tv4.text.toString())
                qRef.update("Q5", binding.tv5.text.toString())
                qRef.update("Q6", binding.tv6.text.toString())
                qRef.update("Q7", binding.tv7.text.toString())
                qRef.update("Q8", binding.tv8.text.toString())
                qRef.update("Q9", binding.tv9.text.toString())
                qRef.update("Q10", binding.tv10.text.toString())
                qRef.update("Q11", binding.tv11.text.toString())
                qRef.update("Q12", binding.tv12.text.toString())
                qRef.update("Q13", binding.tv13.text.toString())
                qRef.update("Q14", binding.tv14.text.toString())
                qRef.update("Q15", binding.tv15.text.toString())
                qRef.update("Q16", binding.tv16.text.toString())
                qRef.update("Q17", binding.tv17.text.toString())
                qRef.update("Q18", binding.tv18.text.toString())
                qRef.update("Q19", binding.tv19.text.toString())
                qRef.update("Q20", binding.tv20.text.toString())
            }
        }

        Log.d("Main", "updateUI worked")
    }

    private fun editQuestion() {
        auth = FirebaseAuth.getInstance()
        val input = EditText(this)
        input.setText(getString(R.string.dialogpretext))

        val dialogBuilder = AlertDialog.Builder(this)
        when (questionToEdit) {
            1 -> {
                dialogBuilder.setMessage("Enter your Question....")
                    .setView(input)
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Confirm", DialogInterface.OnClickListener { _, _ ->
                        binding.tv1.text = input.text.toString()
                        updateQuestion()
                    })
                    // negative button text and action
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            }
            2 -> {
                dialogBuilder.setMessage("Enter your Question....")
                    .setView(input)
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Confirm", DialogInterface.OnClickListener { _, _ ->
                        binding.tv2.text = input.text.toString()
                        updateQuestion()
                    })
                    // negative button text and action
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            }
            3 -> {
                dialogBuilder.setMessage("Enter your Question....")
                    .setView(input)
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Confirm", DialogInterface.OnClickListener { _, _ ->
                        binding.tv3.text = input.text.toString()
                        updateQuestion()
                    })
                    // negative button text and action
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            }
            4 -> {
                dialogBuilder.setMessage("Enter your Question....")
                    .setView(input)
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Confirm", DialogInterface.OnClickListener { _, _ ->
                        binding.tv4.text = input.text.toString()
                        updateQuestion()
                    })
                    // negative button text and action
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            }
            5 -> {
                dialogBuilder.setMessage("Enter your Question....")
                    .setView(input)
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Confirm", DialogInterface.OnClickListener { _, _ ->
                        binding.tv5.text = input.text.toString()
                        updateQuestion()
                    })
                    // negative button text and action
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            }
            6 -> {
                dialogBuilder.setMessage("Enter your Question....")
                    .setView(input)
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Confirm", DialogInterface.OnClickListener { _, _ ->
                        binding.tv6.text = input.text.toString()
                        updateQuestion()
                    })
                    // negative button text and action
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            }
            7 -> {
                dialogBuilder.setMessage("Enter your Question....")
                    .setView(input)
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Confirm", DialogInterface.OnClickListener { _, _ ->
                        binding.tv7.text = input.text.toString()
                        updateQuestion()
                    })
                    // negative button text and action
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            }
            8 -> {
                dialogBuilder.setMessage("Enter your Question....")
                    .setView(input)
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Confirm", DialogInterface.OnClickListener { _, _ ->
                        binding.tv8.text = input.text.toString()
                        updateQuestion()
                    })
                    // negative button text and action
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            }
            9 -> {
                dialogBuilder.setMessage("Enter your Question....")
                    .setView(input)
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Confirm", DialogInterface.OnClickListener { _, _ ->
                        binding.tv9.text = input.text.toString()
                        updateQuestion()
                    })
                    // negative button text and action
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            }
            10 -> {
                dialogBuilder.setMessage("Enter your Question....")
                    .setView(input)
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Confirm", DialogInterface.OnClickListener { _, _ ->
                        binding.tv10.text = input.text.toString()
                        updateQuestion()
                    })
                    // negative button text and action
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            }
            11 -> {
                dialogBuilder.setMessage("Enter your Question....")
                    .setView(input)
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Confirm", DialogInterface.OnClickListener { _, _ ->
                        binding.tv11.text = input.text.toString()
                        updateQuestion()
                    })
                    // negative button text and action
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            }
            12 -> {
                dialogBuilder.setMessage("Enter your Question....")
                    .setView(input)
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Confirm", DialogInterface.OnClickListener { _, _ ->
                        binding.tv12.text = input.text.toString()
                        updateQuestion()
                    })
                    // negative button text and action
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            }
            13 -> {
                dialogBuilder.setMessage("Enter your Question....")
                    .setView(input)
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Confirm", DialogInterface.OnClickListener { _, _ ->
                        binding.tv13.text = input.text.toString()
                        updateQuestion()
                    })
                    // negative button text and action
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            }
            14 -> {
                dialogBuilder.setMessage("Enter your Question....")
                    .setView(input)
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Confirm", DialogInterface.OnClickListener { _, _ ->
                        binding.tv14.text = input.text.toString()
                        updateQuestion()
                    })
                    // negative button text and action
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            }
            15 -> {
                dialogBuilder.setMessage("Enter your Question....")
                    .setView(input)
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Confirm", DialogInterface.OnClickListener { _, _ ->
                        binding.tv15.text = input.text.toString()
                        updateQuestion()
                    })
                    // negative button text and action
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            }
            16 -> {
                dialogBuilder.setMessage("Enter your Question....")
                    .setView(input)
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Confirm", DialogInterface.OnClickListener { _, _ ->
                        binding.tv16.text = input.text.toString()
                        updateQuestion()
                    })
                    // negative button text and action
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            }
            17 -> {
                dialogBuilder.setMessage("Enter your Question....")
                    .setView(input)
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Confirm", DialogInterface.OnClickListener { _, _ ->
                        binding.tv17.text = input.text.toString()
                        updateQuestion()
                    })
                    // negative button text and action
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            }
            18 -> {
                dialogBuilder.setMessage("Enter your Question....")
                    .setView(input)
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Confirm", DialogInterface.OnClickListener { _, _ ->
                        binding.tv18.text = input.text.toString()
                        updateQuestion()
                    })
                    // negative button text and action
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            }
            19 -> {
                dialogBuilder.setMessage("Enter your Question....")
                    .setView(input)
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Confirm", DialogInterface.OnClickListener { _, _ ->
                        binding.tv19.text = input.text.toString()
                        updateQuestion()
                    })
                    // negative button text and action
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            }
            20 -> {
                dialogBuilder.setMessage("Enter your Question....")
                    .setView(input)
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Confirm", DialogInterface.OnClickListener { _, _ ->
                        binding.tv20.text = input.text.toString()
                        updateQuestion()
                    })
                    // negative button text and action
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            }


        }
        val alert = dialogBuilder.create()
        // show alert dialog
        alert.show()
    }
}