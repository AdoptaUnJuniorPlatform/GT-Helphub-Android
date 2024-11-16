package com.alejandro.helphub.data.source.remote.server.response

import com.google.gson.annotations.SerializedName

data class ProfileImageResponse (
    @SerializedName("message") val message:String,
    @SerializedName("id_image") val idImage:String,
    @SerializedName("statusCode") val statusCode:String
)