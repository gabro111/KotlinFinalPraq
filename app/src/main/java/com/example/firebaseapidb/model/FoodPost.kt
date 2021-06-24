package com.example.firebaseapidb.model

import android.net.Uri

data class FoodPost(
    var uploaderId:String = "",
    var title:String = "",
    var description: String = "",
    var recipe:String = "",
    var localImgUri: String = "",
    var remoteImgUri:String = ""
)
