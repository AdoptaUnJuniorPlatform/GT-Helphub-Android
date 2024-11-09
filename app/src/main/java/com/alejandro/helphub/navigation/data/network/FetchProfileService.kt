package com.alejandro.helphub.navigation.data.network

import com.alejandro.helphub.navigation.data.network.clients.FetchProfileClient
import com.alejandro.helphub.navigation.data.network.response.ProfileResponse
import retrofit2.Response
import javax.inject.Inject

class FetchProfileService @Inject constructor(private val fetchProfileClient: FetchProfileClient) {
    suspend fun fetchProfile(): Response<ProfileResponse>{
        return fetchProfileClient.fetchProfile()
    }
}