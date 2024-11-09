package com.alejandro.helphub.features.profile.data.network

import com.alejandro.helphub.features.profile.data.network.clients.ProfileClient
import com.alejandro.helphub.features.profile.data.network.response.SearchResponse
import retrofit2.Response
import javax.inject.Inject

class ProfileService @Inject constructor(private val profileClient: ProfileClient) {
    suspend fun getUserInfo(email: String): Response<SearchResponse> {
        return profileClient.getUserInfo(email)
    }


}