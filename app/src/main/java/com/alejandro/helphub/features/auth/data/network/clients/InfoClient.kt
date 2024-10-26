package com.alejandro.helphub.features.auth.data.network.clients

import com.alejandro.helphub.features.auth.domain.UserData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface InfoClient {
    @GET("/api/helphub/user/{email}")
    suspend fun getUserByEmail(@Path("email")email:String): Response<List<UserDataDTO>>
}
data class UserDataDTO(
    val _id: String,
    val email: String,
    val password: String,
    val nameUser: String,
    val surnameUser: String,
    val phone: String,
    val optionCall: Boolean,
    val showPhone: Boolean,
    val blocked: Boolean,
    val __v: Int
)