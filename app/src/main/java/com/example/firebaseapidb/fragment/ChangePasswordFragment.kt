package com.example.firebaseapidb.fragment

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseapidb.R
import com.example.firebaseapidb.auth.LoginActivity
import com.example.firebaseapidb.dialogFragment.LoginDialogFragment
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FederatedAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ChangePasswordFragment:Fragment(R.layout.fragment_change_password) {
    private lateinit var loginDialogFragment: LoginDialogFragment
    private lateinit var backButton3 : ImageButton
    private lateinit var passwordChangeButton:Button
    private lateinit var textInputPasswordResetLayout:TextInputLayout
    private lateinit var textInputPasswordReResetLayout:TextInputLayout
    private var auth = Firebase.auth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        passwordChangeButton = view.findViewById(R.id.passwordChangeButton)
        textInputPasswordResetLayout = view.findViewById(R.id.textInputPasswordResetLayout)
        textInputPasswordReResetLayout = view.findViewById(R.id.textInputPasswordReResetLayout)
        backButton3 = view.findViewById(R.id.backButton3)
        backButton3.setOnClickListener {
            view.findNavController().navigate(R.id.action_changePasswordFragment_to_settingsFragment)
        }
        passwordChangeButton.setOnClickListener {
            validatePasswordAndDoTheDeed()
        }
    }


    private fun validatePasswordAndDoTheDeed() {
        val currentUser = auth.currentUser

        textInputPasswordResetLayout.error = ""
        textInputPasswordReResetLayout.error = ""
        if (textInputPasswordResetLayout.editText?.text.toString().isEmpty()) {
            textInputPasswordResetLayout.error = "Enter Password"
            return
        }
        if (textInputPasswordReResetLayout.editText?.text.toString().isEmpty()) {
            textInputPasswordReResetLayout.error = "Enter Password"
            return
        }
        if (textInputPasswordReResetLayout.editText?.text.toString() != textInputPasswordReResetLayout.editText?.text.toString()) {
            textInputPasswordReResetLayout.error = "Different From Password"
            return
        }
        currentUser?.updatePassword(textInputPasswordResetLayout.editText?.text.toString())
            ?.addOnSuccessListener {
                val builder: AlertDialog.Builder = this.let {
                    AlertDialog.Builder(it.requireContext())
                }
                builder
                    .setTitle("Password Change")
                    .setMessage("Password Changed Successfully")
                    .setPositiveButton("ok",
                        DialogInterface.OnClickListener { dialog, id ->
                            popActivity()
                        })
                builder.show()

            }?.addOnFailureListener {
                loginDialogFragment = LoginDialogFragment("Password Change",it.message!!)
                loginDialogFragment.show(this.childFragmentManager,"A")

            }
    }

    private fun popActivity(){

        requireView().findNavController().popBackStack()
    }

}