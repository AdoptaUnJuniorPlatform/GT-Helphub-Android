package com.alejandro.helphub.data.source.remote.server.response

import com.google.gson.annotations.SerializedName

data class SkillResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("level") val level: String,
    @SerializedName("mode") val mode: String,
    @SerializedName("description") val description: String,
    @SerializedName("category") val category:List<String>,
    @SerializedName("user_id") val userId:String
    )