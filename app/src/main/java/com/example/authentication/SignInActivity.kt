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
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.progressBar


class SignInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        loginator()
    }
    private fun loginator(){
        auth = Firebase.auth
        logbutton.setOnClickListener {
            signin()
        }
    }
    private fun signin(){
        val email = logmail.text.toString()
        val password = logpassword.text.toString()
        if(email.isNotEmpty() && password.isNotEmpty()){
            progressBar.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        d("signIn", "signInWithEmail:success")
                        val user = auth.currentUser
                        startActivity(Intent(this, mainpage::class.java))
                        overridePendingTransition(R.anim.animation2, R.anim.animation)
                    }
                    else {
                        d("signIn", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, task.exception.toString(),Toast.LENGTH_LONG).show()
                    }
                }
            Toast.makeText(this,"Successful authentication!",Toast.LENGTH_SHORT).show()
        }else
            Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_SHORT).show()
    }
}