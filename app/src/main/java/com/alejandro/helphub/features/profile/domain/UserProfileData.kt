package com.alejandro.helphub.features.profile.domain

import android.graphics.Bitmap
import android.net.Uri

data class UserProfileData (
    val location:String="",
    val description:String="",
    val profilePicture: String="",
    val photoBitmap: Bitmap? = null,
    val preferredTimeRange:String="",
    val selectedDays:List<String> = emptyList(),
    val interestedSkills:List<String> =emptyList()
)