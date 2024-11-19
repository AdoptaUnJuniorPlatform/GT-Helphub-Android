package com.alejandro.helphub.domain.models

data class UserProfileData (
    val id:String="",
    val userId:String="",
    val location:String="",
    val description:String="",
    val profileImage: ByteArray?=null,
    //val photoBitmap: GlyphRenderData.Bitmap? = null,
    val preferredTimeRange:String="",
    val selectedDays:List<String> = emptyList(),
    val interestedSkills:List<String> =emptyList()
)