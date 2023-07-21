package com.example.drinkinggame

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.drinkinggame.databinding.ActivityQuestionSetsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


var questionsetselected = 1
var questionsetedit = 0
var questionsetrename = 0

class QuestionSets : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionSetsBinding
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore
    private var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionSetsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        //Setting Default/Saved Values
        auth = FirebaseAuth.getInstance()
        db.collection("Account Data").document(auth.currentUser?.uid.toString()).collection(
            "Question Set Name Edit"
        ).document("Names").get().addOnSuccessListener { document->
                binding.Qs1.text = document.getString("QS1Name").toString()
                binding.Qs2.text = document.getString("QS2Name").toString()
                binding.Qs3.text = document.getString("QS3Name").toString()

        }
        ///End of Setting Default/Saved Question Set Names

        //Buttons for Edit,Rename,Select
        binding.e1.setOnClickListener {
            questionsetedit = 1
            val intent = Intent(this, QuestionList::class.java)
            startActivity(intent)
        }
        binding.e2.setOnClickListener {
            questionsetedit = 2
            val intent = Intent(this, QuestionList::class.java)
            startActivity(intent)
        }
        binding.e3.setOnClickListener {
            questionsetedit = 3
            val intent = Intent(this, QuestionList::class.java)
            startActivity(intent)
        }
        binding.r1.setOnClickListener {
            questionsetrename = 1
            rename()
        }
        binding.r2.setOnClickListener {
            questionsetrename = 2
            rename()
        }
        binding.r3.setOnClickListener {
            questionsetrename = 3
            rename()
        }

        binding.s1.setOnClickListener {
            clearSelect()
            questionsetselected = 1
            binding.s1.text = "Selected"
            binding.s1.backgroundTintList =
                ContextCompat.getColorStateList(applicationContext, R.color.maroon)
        }
        binding.s2.setOnClickListener {
            clearSelect()
            questionsetselected = 2
            binding.s2.text = "Selected"
            binding.s2.backgroundTintList =
                ContextCompat.getColorStateList(applicationContext, R.color.maroon)
        }
        binding.s3.setOnClickListener {
            clearSelect()
            questionsetselected = 3
            binding.s3.text = "Selected"
            binding.s3.backgroundTintList =
                ContextCompat.getColorStateList(applicationContext, R.color.maroon)
        }

    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
    private fun updateSetName() {
        val qSetNamesref =
            db.collection("Account Data").document(auth.currentUser?.uid.toString()).collection(
                "Question Set Name Edit"
            ).document("Names")
        qSetNamesref.update("QS1Name", binding.Qs1.text.toString())
        qSetNamesref.update("QS2Name", binding.Qs2.text.toString())
        qSetNamesref.update("QS3Name", binding.Qs3.text.toString())
        Log.d("Main", "updateUI worked")
    }

    private fun clearSelect() {
        binding.s1.text = buildString {
            append("Select")
        }
        binding.s2.text = buildString {
            append("Select")
        }
        binding.s3.text = buildString {
            append("Select")
        }
        binding.s1.backgroundTintList =
            ContextCompat.getColorStateList(applicationContext, R.color.IndianRed)
        binding.s2.backgroundTintList =
            ContextCompat.getColorStateList(applicationContext, R.color.IndianRed)
        binding.s3.backgroundTintList =
            ContextCompat.getColorStateList(applicationContext, R.color.IndianRed)
    }

    private fun rename() {
        val input = EditText(this)
        input.hint = "Enter Here"
        val dialogBuilder = AlertDialog.Builder(this)
        when (questionsetrename) {
            1 -> {
                dialogBuilder.setMessage("What would you like to name this Question Set?")
                    .setView(input)
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Confirm", DialogInterface.OnClickListener { _, _ ->
                        binding.Qs1.text = input.text.toString()
                        updateSetName()
                    })
                    // negative button text and action
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            }
            2 -> {
                dialogBuilder.setMessage("What would you like to name this Question Set?")
                    .setView(input)
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Confirm", DialogInterface.OnClickListener { _, _ ->
                        binding.Qs2.text = input.text.toString()
                        updateSetName()
                    })
                    // negative button text and action
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            }
            3 -> {
                dialogBuilder.setMessage("What would you like to name this Question Set?")
                    .setView(input)
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Confirm", DialogInterface.OnClickListener { _, _ ->
                        binding.Qs3.text = input.text.toString()
                        updateSetName()
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