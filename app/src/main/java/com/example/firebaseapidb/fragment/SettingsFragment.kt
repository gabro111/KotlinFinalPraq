package com.example.firebaseapidb.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.firebaseapidb.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SettingsFragment:Fragment(R.layout.fragment_settings) {

private lateinit var signOutButton: Button
    private lateinit var mAuth: FirebaseAuth




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = Firebase.auth

        signOutButton = view.findViewById(R.id.logOutButton)
        signOutButton.setOnClickListener {
            mAuth.signOut()
        }
    }
}