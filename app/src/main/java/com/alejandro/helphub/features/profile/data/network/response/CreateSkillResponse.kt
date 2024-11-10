package com.alejandro.helphub.features.profile.data.network.response

import com.google.gson.annotations.SerializedName

data class CreateSkillResponse (
    @SerializedName("code") val code:String,
    @SerializedName("description") val description:String,

)