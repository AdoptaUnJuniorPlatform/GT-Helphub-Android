package com.alejandro.helphub.data.source.remote.server.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse (
    @SerializedName("message") val message:String,
    @SerializedName("statusCode") val statusCode:Int?,
    @SerializedName("_id") val id:String,
    @SerializedName("userId") val userId: UserId,
    @SerializedName("email") val email:String,
    @SerializedName("nameUser") val nameUser:String,
    @SerializedName("surnameUser") val surnameUser:String,
    @SerializedName("description") val description:String,
    @SerializedName("interestedSkills") val interestedSkills:List<String>,
    @SerializedName("location") val location: String,
    @SerializedName("profilePicture") val profilePicture: ByteArray?,
    @SerializedName("preferredTimeRange") val preferredTimeRange: String,
    @SerializedName("selectedDays") val selectedDays: List<String>,
)
data class UserId(
    @SerializedName("_id") val id:String,
)