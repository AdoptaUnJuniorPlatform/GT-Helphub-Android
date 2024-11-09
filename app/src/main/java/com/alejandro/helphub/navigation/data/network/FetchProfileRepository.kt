package com.alejandro.helphub.navigation.data.network

import com.alejandro.helphub.navigation.data.network.response.ApiResponse
import com.alejandro.helphub.navigation.data.network.response.ProfileResponse
import retrofit2.Response
import javax.inject.Inject

class FetchProfileRepository @Inject constructor(
    private val fetchProfileService: FetchProfileService
) {
    suspend fun fetchProfile(): ApiResponse<ProfileResponse> {
        return fetchProfileService.fetchProfile()
    }
}