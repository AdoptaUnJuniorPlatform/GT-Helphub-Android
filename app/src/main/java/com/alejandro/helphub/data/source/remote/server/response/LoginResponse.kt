package com.alejandro.helphub.data.source.remote.server.response

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("access_token") val accessToken:String
)