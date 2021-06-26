package com.example.firebaseapidb.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseapidb.R
import com.example.firebaseapidb.model.FoodPost
import com.example.firebaseapidb.model.FoodUser
import com.example.firebaseapidb.service.FirebaseStorageService
import com.example.firebaseapidb.viewmodel.FoodPostViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class RecyclerViewAdapter(private val foodPost: List<FoodPost>,private val favoritePosts:List<FoodPost>,
                          private val model: FoodPostViewModel) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {


        class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val recyclerImage:ImageView = itemView.findViewById(R.id.recyclerImage)
            val recyclerTitle:TextView = itemView.findViewById(R.id.recyclerTitle)
            val recyclerDescription:TextView = itemView.findViewById(R.id.recyclerDescription)
            val favoriteButton:ImageButton = itemView.findViewById(R.id.favoriteButton)


    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {

        return RecyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        if(favoritePosts.contains(foodPost[position])){
            holder.favoriteButton.setImageResource( R.drawable.ic_round_star_24)
        }else
        {
            holder.favoriteButton.setImageResource( R.drawable.ic_round_star_border_24)
        }
        holder.recyclerDescription.text = foodPost[position].description
        holder.recyclerTitle.text = foodPost[position].title
        holder.favoriteButton.setOnClickListener {
        model.updateFavoritePosts(foodPost[position])
            notifyDataSetChanged()
//            if(favoritePosts.contains(foodPost[position-1])){
//                val task = model.removeFromFavorite(foodPost[position])
//                task.addOnSuccessListener{
//                    foodPost = model.getFavoritePosts().value!!
//                    holder.favoriteButton.setImageResource( R.drawable.ic_round_star_border_24)
//                }
//            }
//
//            if(!favoritePosts.contains(foodPost[position])){
//                val task = model.addToFavorite(foodPost[position])
//                task.addOnSuccessListener {
//                    foodPost = model.getFavoritePosts().value!!
//                    holder.favoriteButton.setImageResource( R.drawable.ic_round_star_24)
//                }
//
//            }


    }

    }

    override fun getItemCount(): Int =foodPost.size
}