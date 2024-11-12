package com.alejandro.helphub.data.source.remote.server


import com.alejandro.helphub.data.source.remote.dto.auth.LoginDTO
import com.alejandro.helphub.data.source.remote.dto.auth.UserDTO
import com.alejandro.helphub.data.source.remote.server.response.LoginResponse
import com.alejandro.helphub.data.source.remote.server.response.TwofaResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthClient {
    @POST("/api/helphub/user/register")
    suspend fun registerNewUser(@Body userDTO: com.alejandro.helphub.data.source.remote.dto.auth.UserDTO): Response<TwofaResponse>

    @POST("/api/helphub/auth/login-mobile")
    suspend fun doLogin(@Body loginDTO: com.alejandro.helphub.data.source.remote.dto.auth.LoginDTO):Response<LoginResponse>

    @PATCH("/api/helphub/auth/reset-password")
    suspend fun resetPasswordRequest( @Body loginDTO: com.alejandro.helphub.data.source.remote.dto.auth.LoginDTO):Response<TwofaResponse>
}