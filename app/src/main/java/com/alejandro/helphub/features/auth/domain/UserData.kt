package com.alejandro.helphub.features.auth.domain

import android.graphics.Bitmap
import android.net.Uri

data class UserData (
    val nameUser:String="",
    val surnameUser:String="",
    val surname2:String="",
    val email:String="",
    val password:String="",
    val countryCode:String="",
    val phone:String="",
    val optionCall:Boolean=false,
    val showPhone:Boolean=false,
    val blocked:Boolean=false,
    val twoFa:String="",
    val role:String="user",
    val userDescription:String="",
    val postalCode:String="",
    val userPhotoUri: Uri? =null,
    val photoBitmap: Bitmap? = null,
    val availability:String?=null,
    val selectedDays:List<String> = emptyList(),
    val postTitle:String="",
    val selectedLevel:String?=null,
    val mode:String?=null,
    val skillDescription:String="",
    val selectedCategories:List<String> = emptyList(),
    val categoriesOfInterest:List<String> =emptyList()
)