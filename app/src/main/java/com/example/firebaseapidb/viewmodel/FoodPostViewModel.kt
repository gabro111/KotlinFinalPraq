package com.example.firebaseapidb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebaseapidb.model.FoodPost
import com.example.firebaseapidb.model.FoodUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
//First Time Using Android View Model

// TODO: Think Of Logic How To Add Loaders
class FoodPostViewModel : ViewModel() {
    private lateinit var  db:FirebaseFirestore
    private lateinit var auth:FirebaseAuth

    //initialize on first call of ViewModel
    init {
        db = Firebase.firestore
        auth = Firebase.auth
        loadUser()

    }




    //Properties Lazy meaning to do work when we need first instance
    private val foodPosts: MutableLiveData<List<FoodPost>> by lazy {
        MutableLiveData<List<FoodPost>>().also {
            loadPosts()
        }
    }
    private val userFavoritePosts:MutableLiveData<List<FoodPost>> by lazy {
        MutableLiveData<List<FoodPost>>().also {
            loadFavoritePosts()
        }
    }
    private val userLoggedUser: MutableLiveData<FoodUser>  = MutableLiveData()


    //Setters/Loaders
    private fun loadFavoritePosts() {
        val holder :MutableList<FoodPost> = ArrayList()
        db.collection("users")
            .get()
            .addOnSuccessListener { documents ->

                for(document in documents){

                    val item  = document.toObject<FoodUser>()
                    if(item.uId == auth.currentUser?.uid){

                        holder.addAll(item.favoritePost)
                    }


                }

                userFavoritePosts.value = holder

            }
    }
    private fun loadPosts() {
        val holder :MutableList<FoodPost> = ArrayList()
        db.collection("posts")
            .get()
            .addOnSuccessListener  { documents ->

                for(document in documents){
                    holder.add( document.toObject<FoodPost>())

                }
                foodPosts.value = holder

            }


        }
    private fun loadUser(){
        db.collection("users").get().addOnSuccessListener { documents ->

            for(document in documents){
                if(document.toObject<FoodUser>().uId == auth.currentUser?.uid){
                    userLoggedUser.value = document.toObject<FoodUser>()
                }
            }

        }
    }

    //Extended Functions / Helper Functions
    fun addToFavorite(foodPost: FoodPost){
        val loggedUser = userLoggedUser.value
        loggedUser?.favoritePost?.add(foodPost)
        if (loggedUser != null) {
            db.collection("users")
                .document(loggedUser.uId)
                .set(loggedUser)
        }
    }
    fun removeFromFavorite(foodPost: FoodPost){
        val loggedUser = userLoggedUser.value
        loggedUser?.favoritePost?.remove(foodPost)
        if (loggedUser != null) {
            db.collection("users")
                .document(loggedUser.uId)
                .set(loggedUser)
        }
    }





    //Getters
    fun getFavoritePosts():MutableLiveData<List<FoodPost>>{
        return userFavoritePosts
    }
    fun getPosts(): LiveData<List<FoodPost>> {
        return foodPosts
    }
    fun getUser(): LiveData<FoodUser> {
        return userLoggedUser
    }

    }



