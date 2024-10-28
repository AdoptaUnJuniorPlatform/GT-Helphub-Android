package com.alejandro.helphub.features.auth.domain

class LoginDTO(
    val email: String,
    val password: String
)

//Used for login, 2fa login, resetting password and 2fa to reset password