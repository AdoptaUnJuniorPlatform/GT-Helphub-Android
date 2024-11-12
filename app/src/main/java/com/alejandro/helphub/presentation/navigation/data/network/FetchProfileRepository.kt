package com.alejandro.helphub.presentation.navigation.data.network

import com.alejandro.helphub.presentation.navigation.data.network.response.ApiResponse
import com.alejandro.helphub.presentation.navigation.data.network.response.ProfileResponse
import retrofit2.Response
import javax.inject.Inject

class FetchProfileRepository @Inject constructor(
    private val fetchProfileService: FetchProfileService
) {
    suspend fun fetchProfile(): ApiResponse<ProfileResponse> {
        return fetchProfileService.fetchProfile()
    }
}