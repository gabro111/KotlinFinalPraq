package com.example.firebaseapidb.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.firebaseapidb.MainActivity
import com.example.firebaseapidb.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SettingsFragment:Fragment(R.layout.fragment_settings) {

private lateinit var signOutButton: TextView
private lateinit var changePasswordButton: TextView
private lateinit var changeEmailButton:TextView
private lateinit var loggedUserEmail:TextView
    private lateinit var mAuth: FirebaseAuth




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = Firebase.auth
        loggedUserEmail = view.findViewById(R.id.loggedUserEmail)
        signOutButton = view.findViewById(R.id.logOutButton)
        changePasswordButton = view.findViewById(R.id.changePasswordButton)
        changeEmailButton = view.findViewById(R.id.changeEmailButton)
        loggedUserEmail.text = mAuth.currentUser?.email
        changePasswordButton.setOnClickListener {

            view.findNavController().navigate(R.id.action_settingsFragment_to_changePasswordFragment)
        }
        changeEmailButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_settingsFragment_to_changeEmailFragment)
        }
        signOutButton.setOnClickListener {
            mAuth.signOut()
            startActivity(Intent(this.context, MainActivity::class.java))
            this.activity?.finish()

        }
    }
}