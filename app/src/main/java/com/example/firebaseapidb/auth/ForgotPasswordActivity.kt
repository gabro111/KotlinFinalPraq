package com.example.firebaseapidb.auth

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseapidb.R
import com.example.firebaseapidb.dialogFragment.LoginDialogFragment
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgotPasswordActivity: AppCompatActivity(R.layout.activity_forgot_password) {
    private lateinit var mAuth: FirebaseAuth


private lateinit var mainButton :Button
    private lateinit var backButton: ImageButton;
    private lateinit var emailResetEditText: TextInputLayout
    private lateinit var loginDialogFragment: LoginDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = Firebase.auth
        emailResetEditText = findViewById(R.id.emailResetEditText)
        backButton = findViewById(R.id.backButtonSecond)
        mainButton = findViewById(R.id.resetMainPasswordButton)
        backButton.setOnClickListener{
            popActivity()
        }

        val builder: AlertDialog.Builder = this.let {
            AlertDialog.Builder(it)
        }
        builder
            .setTitle("Reset")
            .setMessage("Reset Email Sent Successfully")
            .setPositiveButton("ok",
                DialogInterface.OnClickListener { dialog, id ->
                    popActivity()
                })

        builder.create()

        mainButton.setOnClickListener {
            resetPassword(builder)
        }


    }


    private fun resetPassword(builder:AlertDialog.Builder){
        emailResetEditText.error = ""
        if(emailResetEditText.editText?.text.toString().isEmpty()){
            emailResetEditText.error = "Email is empty"
            return
        }
        mAuth.sendPasswordResetEmail(emailResetEditText.editText?.text.toString())
            .addOnFailureListener {
                loginDialogFragment = LoginDialogFragment(it.message,"Reset Email Error")
                loginDialogFragment.show(this.supportFragmentManager,"asd")
        }.addOnSuccessListener {
                builder.show()
        }
    }

    private fun popActivity(){
        val intent = Intent(this,LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}