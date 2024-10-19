package com.alejandro.helphub.features.auth.domain

import android.graphics.Bitmap
import android.net.Uri

data class UserData (
    val name:String="",
    val surname:String="",
    val email:String="",
    val password:String="",
    val countryCode:String="",
    val phoneNumber:String="",
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
    val selectedCategory:String?=null,
)