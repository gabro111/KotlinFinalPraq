package com.example.firebaseapidb.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseapidb.R
import com.example.firebaseapidb.adapter.SecondRecyclerViewAdapter
import com.example.firebaseapidb.model.FoodPost
import com.example.firebaseapidb.viewmodel.FoodPostViewModel

class FavoriteFragment: Fragment(R.layout.fragment_favorite) {
    private lateinit var recyclerView : RecyclerView
    private  var _foodPosts = ArrayList<FoodPost>()

    private val model: FoodPostViewModel = FoodPostViewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    recyclerView = view.findViewById(R.id.recyclerViewSecond)

        val secondRecyclerViewAdapter = SecondRecyclerViewAdapter(_foodPosts,model)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = secondRecyclerViewAdapter
        recyclerView.itemAnimator= DefaultItemAnimator()

        model.getFavoritePosts().observe(viewLifecycleOwner, { foodPosts ->
            _foodPosts.removeAll(_foodPosts)
            _foodPosts.addAll(foodPosts)
            recyclerView.adapter!!.notifyDataSetChanged()

        })
    }

}