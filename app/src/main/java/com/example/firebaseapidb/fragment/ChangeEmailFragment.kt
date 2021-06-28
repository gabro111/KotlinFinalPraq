package com.example.firebaseapidb.fragment

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.firebaseapidb.R
import com.example.firebaseapidb.auth.LoginActivity
import com.example.firebaseapidb.dialogFragment.LoginDialogFragment
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ChangeEmailFragment:Fragment(R.layout.fragment_change_email) {
    private val auth = Firebase.auth
    private lateinit var loginDialogFragment: LoginDialogFragment
    private lateinit var  textInputEmailResetLayout:TextInputLayout
    private lateinit var emailChangeButton:Button
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private lateinit var backButton4:ImageButton
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton4 = view.findViewById(R.id.backButton4)
        textInputEmailResetLayout = view.findViewById(R.id.textInputEmailResetLayout)
        emailChangeButton = view.findViewById(R.id.emailChangeButton)
        backButton4.setOnClickListener {
            view.findNavController().navigate(R.id.action_changeEmailFragment_to_settingsFragment)
        }

        emailChangeButton = view.findViewById(R.id.emailChangeButton)
        emailChangeButton.setOnClickListener {
            validateNewEmail()
        }

    }

    private fun validateNewEmail(){
        textInputEmailResetLayout.error = ""
        if(textInputEmailResetLayout.editText!!.text.trim{ it <= ' ' }.matches(emailPattern.toRegex())){
            auth.currentUser?.updateEmail(textInputEmailResetLayout.editText!!.text.toString())?.addOnSuccessListener {
                val builder: AlertDialog.Builder = this.let {
                    AlertDialog.Builder(it.requireContext())
                }
                builder
                    .setTitle("Email Change")
                    .setMessage("Email Changed Successfully")
                    .setPositiveButton("ok",
                        DialogInterface.OnClickListener { dialog, id ->
                            popActivity()
                        })
                builder.show()
            }?.addOnFailureListener {
                loginDialogFragment = LoginDialogFragment("Email Change",it.message!!)
                loginDialogFragment.show(this.childFragmentManager,"A")

            }

        }else{
            textInputEmailResetLayout.error ="Fix Email Input"
        }

    }
    private fun popActivity(){
        val intent = Intent(this.context, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        requireView().findNavController().popBackStack()
    }
}