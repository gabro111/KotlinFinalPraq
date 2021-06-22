package com.example.firebaseapidb.auth

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseapidb.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity:AppCompatActivity(R.layout.activity_login) {
    private lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        mAuth = FirebaseAuth.getInstance()


        mAuth.signInWithEmailAndPassword(email,password)

    }


}