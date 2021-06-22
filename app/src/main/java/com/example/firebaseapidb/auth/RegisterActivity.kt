package com.example.firebaseapidb.auth

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseapidb.R

class RegisterActivity:AppCompatActivity(R.layout.activity_register) {

    private lateinit var backButton: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        backButton = findViewById(R.id.backButton)

        backButton.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }
}