package com.example.firebaseapidb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firebaseapidb.R
import com.example.firebaseapidb.fragment.HomeFragmentDirections
import com.example.firebaseapidb.model.FoodPost
import com.example.firebaseapidb.viewmodel.FoodPostViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecyclerViewAdapter(private val foodPost: List<FoodPost>,private val favoritePosts:List<FoodPost>,
                          private val model: FoodPostViewModel) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

    val auth = Firebase.auth


        class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val recyclerImage:ImageView = itemView.findViewById(R.id.recyclerImage)
            val recyclerTitle:TextView = itemView.findViewById(R.id.recyclerTitle)
            val recyclerDescription:TextView = itemView.findViewById(R.id.recyclerDescription)
            val favoriteButton:ImageButton = itemView.findViewById(R.id.favoriteButton)
            val ratingView:TextView = itemView.findViewById(R.id.ratingView)


    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {

        return RecyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
           val action = HomeFragmentDirections.actionHomeFragmentToFoodDetailFragment(foodTitle = foodPost[position].title,foodDescription = foodPost[position].description
            ,foodImgUrl = foodPost[position].remoteImgUri,foodRecipe = foodPost[position].recipe,foodRating = foodPost[position].rating)
            holder.itemView.findNavController().navigate(action)
        }

        if(auth.currentUser!!.uid != foodPost[position].uploaderId) {
            if (favoritePosts.contains(foodPost[position])) {
                holder.favoriteButton.setImageResource(R.drawable.ic_round_star_24)
            } else {
                holder.favoriteButton.setImageResource(R.drawable.ic_round_star_border_24)
            }
        }else{
            holder.favoriteButton.setImageResource(R.drawable.ic_round_remove_circle_24)
        }
        Glide.with(holder.itemView)
            .load(foodPost[position].remoteImgUri)
            .override(250, 350)
            .into(holder.recyclerImage)
        holder.ratingView.text = foodPost[position].rating.toString()
        holder.recyclerDescription.text = foodPost[position].description
        holder.recyclerTitle.text = foodPost[position].title
        holder.favoriteButton.setOnClickListener {


            if(auth.currentUser!!.uid != foodPost[position].uploaderId) {
                if (!favoritePosts.contains(foodPost[position])) {
                    model.updateUserFavoritePosts(foodPost[position])

                } else {
                    model.deleteUserFavoritePosts(foodPost[position])
                }
            }else{
                model.deletePost(foodPost[position])
            }

    }

    }

    override fun getItemCount(): Int =foodPost.size
}