package com.example.firebaseapidb.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseapidb.R
import com.example.firebaseapidb.model.FoodPost
import com.example.firebaseapidb.viewmodel.FoodPostViewModel

class SecondRecyclerViewAdapter(private val foodPost: MutableList<FoodPost>,private val model: FoodPostViewModel):RecyclerView.Adapter<SecondRecyclerViewAdapter.SecondRecyclerViewHolder>()  {

    class SecondRecyclerViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val recyclerImage: ImageView = itemView.findViewById(R.id.secondRecyclerImage)
        val recyclerTitle: TextView = itemView.findViewById(R.id.secondRecyclerTitle)
        val recyclerDescription: TextView = itemView.findViewById(R.id.secondRecyclerDescription)
        val favoriteButton: ImageButton = itemView.findViewById(R.id.secondFavoriteButton)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecondRecyclerViewHolder {
        return SecondRecyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.second_recycler_view_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SecondRecyclerViewHolder, position: Int) {


        val favoritePosts = model.getFavoritePosts().value

        if(favoritePosts?.contains(foodPost[position]) == true){
            holder.favoriteButton.setImageResource( R.drawable.ic_round_star_24)
        }else
        {
            holder.favoriteButton.setImageResource( R.drawable.ic_round_star_border_24)
        }

        holder.recyclerDescription.text = foodPost[position].description
        holder.recyclerTitle.text = foodPost[position].title

        holder.favoriteButton.setOnClickListener{
            model.removeFromFavorite(foodPost[position])
            foodPost.removeAt(position)
            notifyItemRemoved(position)
        }

    }

    override fun getItemCount(): Int = foodPost.size
}