package com.alejandro.helphub.navigation.domain.usecases

import android.util.Log
import com.alejandro.helphub.navigation.data.network.FetchProfileRepository
import com.alejandro.helphub.navigation.data.network.response.ApiResponse
import com.alejandro.helphub.navigation.data.network.response.ProfileResponse
import retrofit2.Response
import javax.inject.Inject

class FetchProfileUseCase @Inject constructor(private val fetchProfileRepository: FetchProfileRepository) {
    suspend operator fun invoke(): ApiResponse<ProfileResponse> {
        return try {
            fetchProfileRepository.fetchProfile()
        } catch (e: Exception) {
            Log.e("FetchProfileUseCase", "Error in request: ${e.message}")
            ApiResponse.Error(
                code = 500,
                message = e.message ?: "Unknown error occurred"
            )
        }
    }
}