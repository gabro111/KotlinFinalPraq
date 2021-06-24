package com.example.firebaseapidb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebaseapidb.model.FoodPost
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class FoodPostViewModel : ViewModel() {
    private val db = Firebase.firestore

    private val foodPosts: MutableLiveData<List<FoodPost>> by lazy {
        MutableLiveData<List<FoodPost>>().also {
            loadUsers()
        }
    }

    fun getPosts(): LiveData<List<FoodPost>> {
        return foodPosts
    }
    private fun loadUsers() {
        var holder :MutableList<FoodPost> = ArrayList()
        db.collection("posts")
            .get()
            .addOnSuccessListener  { documents ->

                for(document in documents){
                    holder.add( document.toObject<FoodPost>())

                }
                foodPosts.value = holder

            }


        }

    }


