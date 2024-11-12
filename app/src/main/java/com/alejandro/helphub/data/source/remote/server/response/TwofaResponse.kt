package com.alejandro.helphub.data.source.remote.server.response

import com.google.gson.annotations.SerializedName

data class TwofaResponse (
    @SerializedName("code") val code:String
)