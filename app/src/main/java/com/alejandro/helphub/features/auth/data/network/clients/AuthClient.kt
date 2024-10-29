package com.alejandro.helphub.features.auth.data.network.clients

import com.alejandro.helphub.features.auth.data.network.response.LoginResponse
import com.alejandro.helphub.features.auth.data.network.response.TwofaResponse
import com.alejandro.helphub.features.auth.domain.LoginDTO
import com.alejandro.helphub.features.auth.domain.UserDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthClient {
    @POST("/api/helphub/user/register")
    suspend fun registerNewUser(@Body userDTO: UserDTO): Response<TwofaResponse>

    @POST("/api/helphub/auth/login-mobile")
    suspend fun doLogin(@Body loginDTO:LoginDTO):Response<LoginResponse>

    @PATCH("/api/helphub/auth/reset-password")
    suspend fun resetPasswordRequest( @Body loginDTO:LoginDTO):Response<TwofaResponse>
}