package com.alejandro.helphub.domain.models

data class SkillData(
    val id:String ="",
    val title: String = "",
    val level: String = "",
    val mode: String = "",
    val description: String = "",
    val category: List<String> = emptyList(),
    val userId:String=""
)