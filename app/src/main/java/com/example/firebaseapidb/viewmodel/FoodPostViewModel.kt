package com.example.firebaseapidb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebaseapidb.model.FoodPost
import com.example.firebaseapidb.model.FoodUser
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage


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
        val holder : MutableList<FoodPost> = ArrayList()
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
    private fun updateUserCollection(foodUser:FoodUser): Task<Void> {

    return db.collection("users")
        .document(foodUser.uId)
        .set(foodUser)

}
    private fun deletePostDocument(foodPost: FoodPost): Task<Void> {

        return db.collection("posts")
            .document(foodPost.title)
            .delete().addOnSuccessListener { return@addOnSuccessListener }

    }
    fun updateUserFavoritePosts(foodPost:FoodPost) {

        val holder : MutableList<FoodPost> = ArrayList()
        db.collection("users")
            .get()
            .addOnSuccessListener { documents ->

                for(document in documents) {
                    val user = document.toObject<FoodUser>()
//                      Why?
                    if (user.uId == auth.currentUser?.uid) {


                        user.favoritePost.add(foodPost)
                        val updateData = updateUserCollection(user)
                        updateData.addOnSuccessListener {
                            return@addOnSuccessListener
                        }
                        holder.addAll(user.favoritePost)

                    }
                }
                userFavoritePosts.value = holder

            }
    }
    fun deleteUserFavoritePosts(foodPost: FoodPost){
        val holder : MutableList<FoodPost> = ArrayList()
        db.collection("users")
            .get()
            .addOnSuccessListener { documents ->

                for(document in documents) {
                    val user = document.toObject<FoodUser>()

                    if (user.uId == auth.currentUser?.uid) {
                        user.favoritePost.remove(foodPost)
                        val updateData = updateUserCollection(user)
                        updateData.addOnSuccessListener {
                            return@addOnSuccessListener
                        }
                        holder.addAll(user.favoritePost)

                    }
                }
                userFavoritePosts.value = holder

            }
    }
    fun deletePost(foodPost: FoodPost) {
        val holder : MutableList<FoodPost> = ArrayList()
        val task = deletePostDocument(foodPost)
        task.addOnSuccessListener {
            db.collection("posts").get().addOnSuccessListener {  documents ->
                val hs = documents.toObjects<FoodPost>()
                holder.addAll(hs)
                foodPosts.value = holder
        }
            }
        db.collection("users").get().addOnSuccessListener {  document ->
            val users = document.toObjects<FoodUser>()
            for(user in users){
                if(user.favoritePost.contains(foodPost)){
                   user.favoritePost.remove(foodPost)
                    val updateData = updateUserCollection(user)
                    updateData.addOnSuccessListener {
                        return@addOnSuccessListener
                    }
                }
            }


        }

    }



    //Getters
    fun getFavoritePosts():LiveData<List<FoodPost>>{
        return userFavoritePosts
    }
    fun getPosts(): LiveData<List<FoodPost>> {
        return foodPosts
    }
    fun getUser(): LiveData<FoodUser> {
        return userLoggedUser
    }



}



