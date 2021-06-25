package com.example.firebaseapidb.model

data class FoodUser(

    var uId:String = "",
    var favoritePost:MutableList<FoodPost> = ArrayList()
)
