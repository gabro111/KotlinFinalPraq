package com.example.firebaseapidb.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.firebaseapidb.R

class FoodDetailFragment:Fragment(R.layout.fragment_food_detail) {
    private lateinit var backButtonFoodDetToHome:ImageButton
    private lateinit var foodItemPictureDetail:ImageView
    private lateinit var detailTitleTextView:TextView
    private lateinit var detailDescriptionTextView:TextView
    private lateinit var detailRecipeTextView:TextView
    private lateinit var starRating1:ImageView
    private lateinit var starRating2:ImageView
    private lateinit var starRating3:ImageView
    private lateinit var starRating4:ImageView
    private lateinit var starRating5:ImageView
    val args: FoodDetailFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backButtonFoodDetToHome = view.findViewById(R.id.backButtonFoodDetToHome)
        foodItemPictureDetail = view.findViewById(R.id.foodItemPictureDetail)
        detailTitleTextView = view.findViewById(R.id.detailTitleTextView)
        detailDescriptionTextView = view.findViewById(R.id.detailDescriptionTextView)
        detailRecipeTextView = view.findViewById(R.id.detailRecipeTextView)
        starRating1 = view.findViewById(R.id.starRating1)
        starRating2 = view.findViewById(R.id.starRating2)
        starRating3 = view.findViewById(R.id.starRating3)
        starRating4 = view.findViewById(R.id.starRating4)
        starRating5 = view.findViewById(R.id.starRating5)

        val list = arrayListOf(starRating1,starRating2,starRating3,starRating4,starRating5)
        detailTitleTextView.text = args.foodTitle
        detailDescriptionTextView.text =args.foodDescription
        detailRecipeTextView.text = args.foodRecipe
        backButtonFoodDetToHome.setOnClickListener {
            popActivity()
        }
        Glide.with(view)
            .load(args.foodImgUrl)
            .into(foodItemPictureDetail)
        for (x:Int in 0 until args.foodRating){
            list[x].setImageResource(R.drawable.ic_round_star_24)
        }


    }
    private fun popActivity(){

        requireView().findNavController().popBackStack()
    }
}