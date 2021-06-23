package com.example.firebaseapidb.fragment

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.contentValuesOf
import androidx.fragment.app.Fragment
import com.example.firebaseapidb.R
import com.example.firebaseapidb.service.FirebaseStorageService
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.ktx.storageMetadata

class AddFoodFragment: Fragment(R.layout.fragment_add_food) {

private lateinit var choosePictureBtn:ImageButton
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var metadata = storageMetadata {
            contentType = "image/jpg"
        }
        val firebaseStorageService: FirebaseStorageService = FirebaseStorageService()
        choosePictureBtn = view.findViewById(R.id.choosePictureBtn)




        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->

           choosePictureBtn.setImageURI(uri)
            if (uri != null) {
                firebaseStorageService.savePicture(uri)
            }
        }
        choosePictureBtn.setOnClickListener {
        getContent.launch("image/*")
        }
    }





}