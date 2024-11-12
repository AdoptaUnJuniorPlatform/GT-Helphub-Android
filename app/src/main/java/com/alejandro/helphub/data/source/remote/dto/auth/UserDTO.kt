package com.alejandro.helphub.data.source.remote.dto.auth

data class UserDTO (
    val email: String,
    val password: String,
    val nameUser: String,
    val surnameUser: String,
    val phone: String,
    val optionCall: Boolean,
    val showPhone: Boolean,
    val blocked: Boolean,
    val twoFa: String,
    val role: String
)