package com.alejandro.helphub.features.profile.domain

class CreateSkillDTO (
    val title:String,
    val level:String,
    val mode:String,
    val description:String,
    val category:List<String> = emptyList()
)