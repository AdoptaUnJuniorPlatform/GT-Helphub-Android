package com.alejandro.helphub.features.auth.data.network.clients

import com.alejandro.helphub.features.auth.data.network.response.TwofaResponse
import com.alejandro.helphub.features.auth.domain.SendTwofaDTO
import com.alejandro.helphub.features.auth.domain.UserDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TwofaClient {
    @POST("/api/helphub/email-service/emailAcount")
    suspend fun sendTwoFaRegister(@Body userDTO: UserDTO): Response<TwofaResponse>

    @POST("/api/helphub/email-service/resetEmail")
    suspend fun sendTwoFaResetPassword(@Body sendTwofaDTO: SendTwofaDTO): Response<TwofaResponse>

    @POST("/api/helphub/email-service/loginEmail")
    suspend fun sendTwoFaLogin(@Body sendTwofaDTO: SendTwofaDTO): Response<TwofaResponse>
}