package com.alejandro.helphub.features.auth.data.network.clients

import com.alejandro.helphub.features.auth.data.network.response.TwofaResponse
import com.alejandro.helphub.features.auth.domain.TwofaDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TwofaClient {
    @POST("/api/helphub/email-service/emailAcount")
    suspend fun sendTwoFaRegister(@Body twofaDTO: TwofaDTO): Response<TwofaResponse>

    @POST("/api/helphub/user/register")
    suspend fun registerNewUser(@Body twofaDTO: TwofaDTO):Response<TwofaResponse>
}

