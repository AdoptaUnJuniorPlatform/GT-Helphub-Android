package com.alejandro.helphub.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class UserAuthData (
    val id:String="",
    val nameUser:String="",
    val surnameUser:String="",
    val surname2:String="",
    val email:String="",
    val password:String="",
    val countryCode:String="",
    val phone:String="",
    val optionCall:Boolean=false,
    val showPhone:Boolean=false,
    val blocked:Boolean=false,
    val twoFa:String="",
    val role:String="user"
)