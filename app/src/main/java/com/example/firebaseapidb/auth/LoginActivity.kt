package com.example.firebaseapidb.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseapidb.MainActivity
import com.example.firebaseapidb.R
import com.example.firebaseapidb.dialogFragment.LoginDialogFragment
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity:AppCompatActivity(R.layout.activity_login) {
    private lateinit var mAuth:FirebaseAuth
    private lateinit var loginButton: Button
    private lateinit var forgotPassword:TextView;
    private lateinit var email:TextInputLayout
    private lateinit var password:TextInputLayout
    private lateinit var imageButton : ImageButton
    private lateinit var loginDialogFragment:LoginDialogFragment
    private lateinit var registerPageTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth= Firebase.auth
        email = findViewById(R.id.textInputEmailLayout)
        imageButton = findViewById(R.id.imageButtonSignIn)
        password=findViewById(R.id.textInputPasswordLayout)
        forgotPassword=findViewById(R.id.forgotPasswordView)
        registerPageTextView = findViewById(R.id.createAccountButton)
        registerPageTextView.setOnClickListener {
            val intent  = Intent(this,RegisterActivity::class.java)
            startActivity(intent)

        }
        imageButton.setOnClickListener {
            signIn()
        }

        forgotPassword.setOnClickListener {
            val intent  = Intent(this,ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

    }




    private fun signIn() {
        email.error=""
        password.error = ""
        if(email.editText?.text.toString().isEmpty()){
            email.error = "Enter Email Address"
            return
        }
        if (password.editText?.text.toString().isEmpty()) {
            password.error = "Enter Password"
            return
        }

        mAuth.signInWithEmailAndPassword(email.editText?.text.toString(),password.editText?.text.toString())
            .addOnFailureListener{
                            loginDialogFragment = LoginDialogFragment(it.message,"Login Failed")
                            loginDialogFragment.show(this.supportFragmentManager,"A")
        }.addOnCompleteListener {
            startActivity(Intent(this,MainActivity::class.java))
                finish()
        }
    }
}