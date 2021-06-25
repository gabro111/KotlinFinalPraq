package com.example.firebaseapidb.service

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.view.View
import android.widget.ProgressBar
import com.example.firebaseapidb.model.FoodPost
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class FirebaseStorageService {
    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private val storage = Firebase.storage
    private val storeRef = storage.reference

    private fun savePicture(uri:Uri,post:FoodPost){
//         With bytes
//        storage.reference.child("images/${auth.currentUser?.uid}/${}").putBytes(data)
       val imageRef = storeRef.child("images/${auth.currentUser?.uid}/${uri.lastPathSegment}")
        val uploadTask = imageRef.putFile(uri)
        uploadTask.addOnSuccessListener {
            val downloadUrl = imageRef.downloadUrl
            downloadUrl.addOnSuccessListener {
               post.remoteImgUri = it.toString()
                updateDatabase(post)
            }
        }

    }

    private fun updateDatabase(post: FoodPost) {
        db.collection("posts")
            .document(post.title)
            .set(post)
    }


    fun storeFoodRecommendation(post:FoodPost,context: Context,progressBar: ProgressBar){
        progressBar.visibility = View.VISIBLE
        //TODO add check if post exists

        db.collection("posts").document(post.title).set(post, SetOptions.merge())
            .addOnSuccessListener {
                savePicture(Uri.parse(post.localImgUri),post)
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
}