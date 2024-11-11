package com.alejandro.helphub.features.profile.domain

import android.net.Uri

class CreateProfileDTO(
    val description:String,
    val interestedSkills:List<String> = emptyList(),
    val location:String,
    val profilePicture: String,
    val preferredTimeRange:String,
    val selectedDays:List<String> = emptyList()
)