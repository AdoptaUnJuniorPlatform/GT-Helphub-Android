package com.alejandro.helphub.features.auth.data.network.response

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("access_token") val access_token:String
)