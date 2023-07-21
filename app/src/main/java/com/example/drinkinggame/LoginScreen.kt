package com.example.drinkinggame


import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.javafaker.Faker
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginScreen : AppCompatActivity() {
    private lateinit var errorInfo: TextView
    private lateinit var lEmail: TextInputEditText
    private lateinit var lPassword: TextInputEditText
    private lateinit var bLogin: Button
    private lateinit var bRegister: Button
    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore
    private var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        val linearLayout = findViewById<LinearLayout>(R.id.loginlayout)
        val animationDrawable = linearLayout.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(10)
        animationDrawable.setExitFadeDuration(2000)
        animationDrawable.start()

        auth = FirebaseAuth.getInstance()
        errorInfo = findViewById(R.id.ErrorInfo)
        lEmail = findViewById(R.id.emailInput)
        lPassword = findViewById(R.id.passwordInput)
        bLogin = findViewById(R.id.log_in)
        bRegister = findViewById(R.id.register)

        errorInfo.visibility = View.INVISIBLE


        bLogin.setOnClickListener {
            loginAccount()
        }
        bRegister.setOnClickListener {
            registerAccount()
        }
    }

    private fun loginAccount() {
        val email = lEmail.text.toString().trim()
        val psw = lPassword.text.toString().trim()

        if (email.isEmpty() || psw.isEmpty()) {
            Toast.makeText(this, "Please Enter Email or Password", Toast.LENGTH_SHORT).show()
            return
        }
        Log.d("Main", "Email:$email")
        Log.d("Main", "Password: $psw")
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, psw)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                Log.d("Main", "Successfully Login: ${it.result?.user?.uid}")
                val intent = Intent(this, InitialScreen::class.java)
                startActivity(intent)
            }
            .addOnFailureListener {
                errorInfo.visibility = View.VISIBLE
                Log.d("Main", "Failed to Login User: ${it.message}")
            }
    }

    private fun registerAccount() {
        val faker = Faker()
        val name = faker.app().name() // Miss Samanta Schmidt
        val email = lEmail.text.toString().trim()
        val psw = lPassword.text.toString().trim()
        val icon = "brandy"
        if (email.isEmpty() || psw.isEmpty()) {
            Toast.makeText(this, "Please Enter Email or Password", Toast.LENGTH_SHORT).show()
            return
        }
        val emailPsw = hashMapOf(
            "Email" to email,
            "Password" to psw,
            "Display Name" to name,
            "Icon" to icon
        )
        val cleanQuestionSetNames = hashMapOf(
            "QS1Name" to "Question Set 1",
            "QS2Name" to "Question Set 2",
            "QS3Name" to "Question Set 3"
        )
        val defaultQuestions = hashMapOf(
            "Q1" to "Never have I ever urinated in public.....",
            "Q2" to "Never have I ever stolen anything.....",
            "Q3" to "Never have I ever lost a bet.....",
            "Q4" to "Never have I ever broken a bone.....",
            "Q5" to "Never have I ever used a fake ID....",
            "Q6" to "Never have I ever broken up with someone.....",
            "Q7" to "Never have I ever had a one-night stand......",
            "Q8" to "Never have I ever tried psychedelics......",
            "Q9" to "Never have I ever stood up a date......",
            "Q10" to "Never have I ever ghosted someone......",
            "Q11" to "Never have I ever had to take a walk of shame......",
            "Q12" to "Never have I ever snooped through someone’s stuff.....",
            "Q13" to "Never have I ever dined and dashed.......",
            "Q14" to "Never have I ever fought in public......",
            "Q15" to "Never have I ever lied about not doing something I was suppose to do....",
            "Q16" to "Never have I ever pretended to speak a foreign language I didn't know",
            "Q17" to "Never have I ever gone on a blind date.....",
            "Q18" to "Never have I ever made a prank phone call....",
            "Q19" to "Never have I ever lied to get something cheaper than it was.....",
            "Q20" to "Never have I ever talked my way out of getting in trouble....."
        )
        val cleanQuestions = hashMapOf(
            "Q1" to "Never have I ever ",
            "Q2" to "Never have I ever ",
            "Q3" to "Never have I ever ",
            "Q4" to "Never have I ever ",
            "Q5" to "Never have I ever ",
            "Q6" to "Never have I ever ",
            "Q7" to "Never have I ever ",
            "Q8" to "Never have I ever ",
            "Q9" to "Never have I ever ",
            "Q10" to "Never have I ever ",
            "Q11" to "Never have I ever ",
            "Q12" to "Never have I ever ",
            "Q13" to "Never have I ever ",
            "Q14" to "Never have I ever ",
            "Q15" to "Never have I ever ",
            "Q16" to "Never have I ever ",
            "Q17" to "Never have I ever ",
            "Q18" to "Never have I ever ",
            "Q19" to "Never have I ever ",
            "Q20" to "Never have I ever "
        )
        Log.d("Main", "Email:$email")
        Log.d("Main", "Password: $psw")
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, psw)
            .addOnCompleteListener {

                db.collection("Account Data").document(auth.currentUser?.uid.toString()).collection(
                    "Question Sets"
                ).document("Set1").set(defaultQuestions)
                db.collection("Account Data").document(auth.currentUser?.uid.toString()).collection(
                    "Question Sets"
                ).document("Set2").set(cleanQuestions)
                db.collection("Account Data").document(auth.currentUser?.uid.toString()).collection(
                    "Question Sets"
                ).document("Set3").set(cleanQuestions)
                db.collection("Account Data").document(auth.currentUser?.uid.toString()).collection(
                    "Question Set Name Edit"
                ).document("Names").set(cleanQuestionSetNames)
                db.collection("Account Data").document(auth.currentUser?.uid.toString())
                    .set(emailPsw)
                val intent = Intent(this, InitialScreen::class.java)
                startActivity(intent)
                Log.d("Main", "Successfully created user with uid: ${it.result?.user?.uid}")
            }
            .addOnFailureListener {
                Log.d("Main", "Failed to create user: ${it.message}")

            }

    }
}