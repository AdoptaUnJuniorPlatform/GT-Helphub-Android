package com.alejandro.helphub.features.auth.data.network.response

import com.google.gson.annotations.SerializedName

data class TwofaResponse (
    @SerializedName("code") val code:String
)