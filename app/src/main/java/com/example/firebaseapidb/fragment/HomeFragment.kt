package com.example.firebaseapidb.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseapidb.R
import com.example.firebaseapidb.adapter.RecyclerViewAdapter
import com.example.firebaseapidb.model.FoodPost
import com.example.firebaseapidb.model.FoodUser
import com.example.firebaseapidb.viewmodel.FoodPostViewModel
import com.google.firebase.firestore.auth.User


class HomeFragment:Fragment(R.layout.fragment_home) {
private lateinit var recyclerView : RecyclerView
    private val model: FoodPostViewModel by viewModels()
private lateinit var progressBar2:ProgressBar
private  var _foodPosts = ArrayList<FoodPost>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerViewMain)
        progressBar2 = view.findViewById(R.id.progressBar2)

        val recyclerViewAdapter = RecyclerViewAdapter(_foodPosts,model)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = recyclerViewAdapter
        progressBar2.visibility = ProgressBar.GONE

        recyclerView.itemAnimator= DefaultItemAnimator()
        model.getPosts().observe(viewLifecycleOwner, { foodPosts ->
            _foodPosts.removeAll(_foodPosts)
            _foodPosts.addAll(foodPosts)
            recyclerView.adapter!!.notifyDataSetChanged()
        })

    }

}