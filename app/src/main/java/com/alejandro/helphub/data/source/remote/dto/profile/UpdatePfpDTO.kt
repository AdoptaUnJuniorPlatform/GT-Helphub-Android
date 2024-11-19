package com.alejandro.helphub.data.source.remote.dto.profile

import com.google.gson.annotations.SerializedName

class UpdatePfpDTO(
    @SerializedName("id_user") val idUser:String,
    @SerializedName("image_profile") val imageProfile: ByteArray?
)
