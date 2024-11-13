package com.alejandro.helphub.data.source.remote.server


import com.alejandro.helphub.data.source.remote.dto.auth.LoginDTO
import com.alejandro.helphub.data.source.remote.dto.auth.UserDTO
import com.alejandro.helphub.data.source.remote.server.response.LoginResponse
import com.alejandro.helphub.data.source.remote.server.response.ProfileResponse
import com.alejandro.helphub.data.source.remote.server.response.TwofaResponse
import com.alejandro.helphub.data.source.remote.server.response.UserId
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthClient {
    @POST("/api/helphub/user/register")
    suspend fun registerNewUser(@Body userDTO:UserDTO): Response<TwofaResponse>

    @POST("/api/helphub/auth/login-mobile")
    suspend fun doLogin(@Body loginDTO:LoginDTO): Response<LoginResponse>

    @PATCH("/api/helphub/auth/reset-password")
    suspend fun resetPasswordRequest(@Body loginDTO:LoginDTO): Response<TwofaResponse>

    @GET("/api/helphub/user/user-id/{id}")
    suspend fun getUserById(@Path("id")userId:String):Response<ProfileResponse>
}