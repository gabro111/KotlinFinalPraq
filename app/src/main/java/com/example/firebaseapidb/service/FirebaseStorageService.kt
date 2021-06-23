package com.example.firebaseapidb.service

import android.net.Uri
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.ktx.storageMetadata

class FirebaseStorageService {
    private val db = Firebase.firestore
    private val storage = Firebase.storage
    var metadata = storageMetadata {
        contentType = "image/jpg"
    }

    fun savePicture(uri:Uri){
        storage.reference.child("images/${uri.lastPathSegment}").putFile(uri, metadata)
    }


}