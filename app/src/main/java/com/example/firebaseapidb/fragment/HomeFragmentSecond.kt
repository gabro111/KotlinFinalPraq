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

class HomeFragmentSecond: Fragment(R.layout.fragment_home_second) {
    private lateinit var recyclerView : RecyclerView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
recyclerView = view.findViewById(R.id.recyclerViewSecond)

        val model: FoodPostViewModel by viewModels()

        model.getFavoritePosts().observe(this, Observer<List<FoodPost>> { foodPosts ->
            val recyclerViewAdapter = RecyclerViewAdapter(foodPosts)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = recyclerViewAdapter

        })
    }
}