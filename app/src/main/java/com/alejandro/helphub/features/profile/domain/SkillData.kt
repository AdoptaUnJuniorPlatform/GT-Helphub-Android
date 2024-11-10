package com.alejandro.helphub.features.profile.domain

data class SkillData (
    val title:String="",
   // val selectedLevel:String?=null,
    val level:String="",
  //  val mode:String?=null,
    val mode:String="",
    val description:String="",
    val category:List<String> = emptyList()
)