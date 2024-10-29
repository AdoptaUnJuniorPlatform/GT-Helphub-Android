package com.alejandro.helphub.features.auth.domain

class SendTwofaResetPasswordDTO (
    val email: String,
    val twoFa: String
)