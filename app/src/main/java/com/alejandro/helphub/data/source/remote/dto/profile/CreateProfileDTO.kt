package com.alejandro.helphub.data.source.remote.dto.profile

class CreateProfileDTO(
    val description:String,
    val interestedSkills:List<String> = emptyList(),
    val location:String,
    val preferredTimeRange:String,
    val selectedDays:List<String> = emptyList()
)