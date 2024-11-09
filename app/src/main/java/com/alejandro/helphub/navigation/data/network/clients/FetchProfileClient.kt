package com.alejandro.helphub.navigation.data.network.clients

import com.alejandro.helphub.navigation.data.network.response.ProfileResponse
import retrofit2.Response
import retrofit2.http.GET

interface FetchProfileClient {
    @GET("/api/helphub/user/profile")
    suspend fun fetchProfile(): Response<ProfileResponse>
}