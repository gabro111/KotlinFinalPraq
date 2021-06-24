package com.example.firebaseapidb.model

data class FoodUser(

    var uId:String,
    var favoritePost:List<FoodPost> = ArrayList()
)
