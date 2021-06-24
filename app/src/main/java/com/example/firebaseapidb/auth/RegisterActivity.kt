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
import com.example.firebaseapidb.model.FoodUser
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity:AppCompatActivity(R.layout.activity_register) {
    private lateinit var mAuth: FirebaseAuth

    private lateinit var backButton: ImageButton
    private lateinit var registerButton :Button
    private lateinit var textInputEmailRegisterLayout: TextInputLayout
    private lateinit var textInputPasswordRegisterLayout: TextInputLayout
    private lateinit var textInputPasswordReRegisterLayout: TextInputLayout
    private lateinit var loginDialogFragment:LoginDialogFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = Firebase.auth
        registerButton = findViewById(R.id.registrationButton)
        backButton = findViewById(R.id.backButtonSecond)
        backButton.setOnClickListener {
            popActivity()
        }
        val builder: AlertDialog.Builder = this.let {
            AlertDialog.Builder(it)
        }
        builder
            .setTitle("Register")
            .setMessage("User Created Successfully")
            .setPositiveButton("ok",
        DialogInterface.OnClickListener { dialog, id ->
            popActivity()
        })

        builder.create()


        registerButton.setOnClickListener{
            validateRegistration(builder)
        }

        textInputEmailRegisterLayout = findViewById(R.id.textInputEmailRegisterLayout)
        textInputPasswordRegisterLayout = findViewById(R.id.textInputPasswordRegisterLayout)
        textInputPasswordReRegisterLayout = findViewById(R.id.textInputPasswordReRegisterLayout)
    }
    private fun validateRegistration (builder:AlertDialog.Builder){
        textInputEmailRegisterLayout.error = ""
        textInputPasswordRegisterLayout.error = ""
        textInputPasswordReRegisterLayout.error = ""
        if( textInputEmailRegisterLayout.editText?.text.toString().isEmpty()){
           textInputEmailRegisterLayout.error = "Enter Email Address"
           return
       }
        if (textInputPasswordRegisterLayout.editText?.text.toString().isEmpty()) {
            textInputPasswordRegisterLayout.error = "Enter Password"
            return
        }
        if (textInputPasswordReRegisterLayout.editText?.text.toString().isEmpty()) {
            textInputPasswordReRegisterLayout.error = "Enter Password"
            return
        }
        if(textInputPasswordReRegisterLayout.editText?.text.toString() != textInputPasswordRegisterLayout.editText?.text.toString()){
            textInputPasswordReRegisterLayout.error = "Different From Password"
            return
        }



        mAuth.createUserWithEmailAndPassword( textInputEmailRegisterLayout.editText?.text.toString(),textInputPasswordRegisterLayout.editText?.text.toString())
            .addOnSuccessListener {
                val foodUser = FoodUser(mAuth.currentUser?.uid.toString())
                val db = Firebase.firestore
                db.collection("users").add(foodUser)
                builder.show()

            }
            .addOnFailureListener {
                loginDialogFragment = LoginDialogFragment(it.message,"Registration Failed")
                loginDialogFragment.show(this.supportFragmentManager,"A")
            }

    }
    private fun popActivity(){
        val intent = Intent(this,LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}