package com.example.firebaseapidb.service

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.view.View
import android.widget.ProgressBar
import androidx.core.net.toFile
import com.example.firebaseapidb.model.FoodPost
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.ktx.storageMetadata

class FirebaseStorageService {
    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private val storage = Firebase.storage


    private fun savePicture(data:ByteArray){

        storage.reference.child("images/${auth.currentUser?.uid}/${data}").putBytes(data)
    }



    fun storeFoodRecommendation(post:FoodPost,context: Context,progressBar: ProgressBar,data:ByteArray){
        progressBar.visibility = View.VISIBLE
        //TODO add check if post exists
//        if(db.collection("posts").document(post.title).get().isSuccessful){
//            val builder : AlertDialog.Builder = AlertDialog.Builder(context)
//
//            builder.setTitle("Failed")
//                .setMessage("Post title exists ")
//                .setPositiveButton("ok",DialogInterface.OnClickListener{dialog, id ->
//
//                }).show()
//            return
//
//        }
        db.collection("posts").document(post.title).set(post)
            .addOnSuccessListener {
                savePicture(data)
                val builder : AlertDialog.Builder = AlertDialog.Builder(context)
                builder.setTitle("Post Added")
                    .setMessage("Post Successfully ")
                    .setPositiveButton("ok",DialogInterface.OnClickListener{dialog, id ->
                    }).show()
                progressBar.visibility = View.INVISIBLE
            }
            .addOnFailureListener {
                val builder : AlertDialog.Builder = AlertDialog.Builder(context)
                progressBar.visibility = View.INVISIBLE
                builder.setTitle("Error")
                    .setMessage(it.message)
                    .setPositiveButton("ok",DialogInterface.OnClickListener{dialog, id ->
                    }).show()
            }
    }

    fun getStoreRecommendations(){}
}