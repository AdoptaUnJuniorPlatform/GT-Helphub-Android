package com.alejandro.helphub.domain.models

import com.google.gson.annotations.SerializedName

data class ProfileImageData(
    @SerializedName("id_user") val idUser: String, // Cambiado para coincidir con el backend
    @SerializedName("image_profile") val imageProfile: String // Cambiado para coincidir con el backend
)