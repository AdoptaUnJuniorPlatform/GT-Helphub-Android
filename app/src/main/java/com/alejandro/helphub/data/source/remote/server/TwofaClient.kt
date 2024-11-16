package com.alejandro.helphub.data.source.remote.server

import com.alejandro.helphub.data.source.remote.dto.auth.SendTwofaDTO
import com.alejandro.helphub.data.source.remote.dto.auth.UserDTO
import com.alejandro.helphub.data.source.remote.server.response.TwofaResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TwofaClient {
    @POST("/api/helphub/email-service/emailAcount")
    suspend fun sendTwoFaRegister(@Body userDTO:UserDTO): Response<TwofaResponse>

    @POST("/api/helphub/email-service/resetEmail")
    suspend fun sendTwoFaResetPassword(@Body sendTwofaDTO:SendTwofaDTO): Response<TwofaResponse>

    @POST("/api/helphub/email-service/loginEmail")
    suspend fun sendTwoFaLogin(@Body sendTwofaDTO:SendTwofaDTO): Response<TwofaResponse>
}