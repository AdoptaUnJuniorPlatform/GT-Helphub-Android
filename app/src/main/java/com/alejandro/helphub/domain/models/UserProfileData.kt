package com.alejandro.helphub.domain.models


data class UserProfileData (
    val location:String="",
    val description:String="",
    val profilePicture: String="",
    //val photoBitmap: GlyphRenderData.Bitmap? = null,
    val preferredTimeRange:String="",
    val selectedDays:List<String> = emptyList(),
    val interestedSkills:List<String> =emptyList()
)