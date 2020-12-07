package com.example.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        authenticator()
    }

    private fun authenticator(){
        auth = Firebase.auth
        regbutton.setOnClickListener {
            signup()
        }
    }

    private fun signup() {
        val email: String = mail.text.toString()
        val password: String = password.text.toString()
        val repeatPassword: String = reppassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty() && repeatPassword.isNotEmpty()) {
            if (password == repeatPassword)
                if ("@" in email && "." in email) {
                    progressBar.visibility = View.VISIBLE
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this) { task ->
                                progressBar.visibility = View.GONE
                                if (task.isSuccessful) {
                                    d("signUp", "createUserWithEmail:success")
                                    val user = auth.currentUser
                                    startActivity(Intent(this, SignInActivity::class.java))
                                    overridePendingTransition(R.anim.animation2, R.anim.animation)
                                } else {
                                    d("signUp", "createUserWithEmail:failure", task.exception)
                                    Toast.makeText(baseContext, task.exception.toString(),Toast.LENGTH_LONG).show()
                                }
                            }
                    Toast.makeText(this, "Successful SignUp!", Toast.LENGTH_SHORT).show()}
                else
                    Toast.makeText(this, "Email format is not correct", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()

        } else
            Toast.makeText(this, "Please fill all the fields!", Toast.LENGTH_SHORT).show()
    }
}