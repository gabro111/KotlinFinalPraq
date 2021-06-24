package com.example.firebaseapidb.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseapidb.R
import com.example.firebaseapidb.adapter.RecyclerViewAdapter
import com.example.firebaseapidb.model.FoodPost
import com.example.firebaseapidb.viewmodel.FoodPostViewModel
import com.google.firebase.firestore.auth.User


class HomeFragment:Fragment(R.layout.fragment_home) {
private lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerViewMain)

        val dummyData : MutableList<FoodPost> = ArrayList()

        dummyData.add(FoodPost("asd","dsad","dasd","dasda","dasdas","dasda"))




        val model: FoodPostViewModel by viewModels()
        model.getPosts().observe(this, Observer<List<FoodPost>>{ foodPost ->


            val recyclerViewAdapter = RecyclerViewAdapter(foodPost)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = recyclerViewAdapter




        })






    }

}