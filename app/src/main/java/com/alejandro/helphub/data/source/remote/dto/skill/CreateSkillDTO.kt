package com.alejandro.helphub.data.source.remote.dto.skill

class CreateSkillDTO (
    val title:String,
    val level:String,
    val mode:String,
    val description:String,
    val category:List<String> = emptyList()
)