package com.alejandro.helphub.features.auth.data.network.clients

import com.alejandro.helphub.features.auth.data.network.response.TwofaResponse
import com.alejandro.helphub.features.auth.domain.UserData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TwofaClient {
    @POST("/email-service/emailAcount")
    suspend fun twofaUser(@Body userData: UserData): Response<TwofaResponse>
}