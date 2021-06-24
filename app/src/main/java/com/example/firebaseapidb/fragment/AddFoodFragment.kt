package com.example.firebaseapidb.fragment

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.firebaseapidb.R
import com.example.firebaseapidb.dialogFragment.LoginDialogFragment
import com.example.firebaseapidb.model.FoodPost
import com.example.firebaseapidb.service.FirebaseStorageService
import com.example.firebaseapidb.viewmodel.FoodPostViewModel
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class AddFoodFragment: Fragment(R.layout.fragment_add_food) {

    val model: FoodPostViewModel by viewModels()
    private val firebaseStorageService:FirebaseStorageService = FirebaseStorageService()
private lateinit var choosePictureBtn:ImageButton
private lateinit var publishButton:Button
private lateinit var textInputRecipeLayout:TextInputLayout
private lateinit var textInputDescriptionLayout:TextInputLayout
private lateinit var textInputTitleLayout:TextInputLayout
private lateinit var progressBar: ProgressBar
    var foodPost = FoodPost()
    var auth = Firebase.auth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        choosePictureBtn = view.findViewById(R.id.choosePictureBtn)
        publishButton = view.findViewById(R.id.publishButton)
        progressBar = view.findViewById(R.id.progressBar)

        textInputTitleLayout = view.findViewById(R.id.textInputTitleLayout)
        textInputRecipeLayout = view.findViewById(R.id.textInputRecipeLayout)
        textInputDescriptionLayout = view.findViewById(R.id.textInputDescriptionLayout)
        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                foodPost.localImgUri = uri.toString()
                choosePictureBtn.setImageURI(uri)
            }
        }
        choosePictureBtn.setOnClickListener {
        getContent.launch("image/*")
        }
        publishButton.setOnClickListener {


            validateInputAndSave()

        }

    }

  private fun validateInputAndSave(){

        val title = textInputTitleLayout.editText?.text.toString()
        val recipe = textInputRecipeLayout.editText?.text.toString()
        val description = textInputDescriptionLayout.editText?.text.toString()

        if(title.isEmpty() || recipe.isEmpty() || description.isEmpty()){
            val loginDialogFragment : LoginDialogFragment = LoginDialogFragment("Error","Not All Fields Are Filled")
            loginDialogFragment.show(this.childFragmentManager,"Error")
        return
        }

        if(choosePictureBtn.drawable == null) {
            val loginDialogFragment : LoginDialogFragment = LoginDialogFragment("Error","Choose a picture")
            loginDialogFragment.show(this.childFragmentManager,"Error")

            return
        }
        foodPost.title = title
        foodPost.description = description
        foodPost.recipe = recipe
        foodPost.uploaderId = auth.currentUser?.uid.toString()

      //Add Food Using bitMap
//      val bitmap = (choosePictureBtn.drawable as BitmapDrawable).bitmap
//      val baos = ByteArrayOutputStream()
//      bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
//      val data = baos.toByteArray()




      context?.let {
          firebaseStorageService.storeFoodRecommendation(post = foodPost,
              it,progressBar)
      }
      textInputTitleLayout.editText?.text?.clear()
      textInputRecipeLayout.editText?.text?.clear()
      textInputDescriptionLayout.editText?.text?.clear()
      foodPost = FoodPost()

  }





}