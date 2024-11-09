package com.alejandro.helphub.navigation.data.network.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse (
    @SerializedName("message") val message:String,
    @SerializedName("statusCode") val statusCode:Int
)