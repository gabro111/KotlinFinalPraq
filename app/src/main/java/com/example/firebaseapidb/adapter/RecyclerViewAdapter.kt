package com.example.firebaseapidb.adapter

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
import com.example.firebaseapidb.viewmodel.FoodPostViewModel

class RecyclerViewAdapter(private val foodPost: List<FoodPost>,private val model:FoodPostViewModel) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {


        class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

            val recyclerImage:ImageView = itemView.findViewById(R.id.recyclerImage)
            val recyclerTitle:TextView = itemView.findViewById(R.id.recyclerTitle)
            val recyclerDescription:TextView = itemView.findViewById(R.id.recyclerDescription)
            val favoriteButton:ImageButton = itemView.findViewById(R.id.favoriteButton)
        fun addToFavorite(){

        }




    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {



      val favoritePosts = model.getFavoritePosts().value
        if(favoritePosts?.contains(foodPost[position]) == true){
            holder.favoriteButton.setImageResource( R.drawable.ic_round_star_24)
        }else
        {
            holder.favoriteButton.setImageResource( R.drawable.ic_round_star_border_24)
        }

        holder.recyclerDescription.text = foodPost[position].description
        holder.recyclerTitle.text = foodPost[position].title
        holder.favoriteButton.setOnClickListener {

            if(favoritePosts?.contains(foodPost[position]) == true){
                model.removeFromFavorite(foodPost[position])
                holder.favoriteButton.setImageResource( R.drawable.ic_round_star_border_24)
            }else{
                model.addToFavorite(foodPost[position])
                holder.favoriteButton.setImageResource( R.drawable.ic_round_star_24)

            }


    }

    }

    override fun getItemCount(): Int =foodPost.size
}