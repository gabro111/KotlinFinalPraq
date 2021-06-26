package com.example.firebaseapidb.service

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.example.firebaseapidb.model.FoodPost
import com.example.firebaseapidb.model.FoodUser
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
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



     fun taskToGetUser(): Task<DocumentSnapshot> {
        val loggedUser = auth.currentUser?.uid
        return db.collection("users").document(loggedUser!!).get()
    }
//    fun getFavoriteService() : List<FoodPost>{
//        var userASD = FoodUser()
//
//
//    }

    fun addToFav(foodPost: FoodPost): Task<DocumentSnapshot> {

        return taskToGetUser().addOnSuccessListener { user ->

            user.toObject<FoodUser>()?.let { taskToAddToFavorite(foodPost, it) }
        }
    }


    private fun taskToAddToFavorite(foodPost: FoodPost, user: FoodUser): Task<Void> {
        user.favoritePost.add(foodPost)
       return db.collection("user").document(user.uId).set(user)
    }


    fun addToFavorite(foodPost: FoodPost) {
        val loggedUser = auth.currentUser?.uid

        db.collection("users").document(loggedUser!!).get().addOnSuccessListener { user ->
        val userASD = user.toObject<FoodUser>()

            userASD?.favoritePost?.add(foodPost)
            if (userASD != null) {
                db.collection("users")
                    .document(loggedUser)
                    .set(userASD).addOnSuccessListener {
                        return@addOnSuccessListener
                    }
            }


        }


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