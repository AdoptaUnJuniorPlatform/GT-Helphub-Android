package com.alejandro.helphub.data.source.remote.server.response

import com.google.gson.annotations.SerializedName

data class CreateSkillResponse (
    @SerializedName("code") val code:String,
    @SerializedName("description") val description:String,
)