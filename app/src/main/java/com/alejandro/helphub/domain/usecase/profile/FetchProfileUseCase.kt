package com.alejandro.helphub.domain.usecase.profile

import android.util.Log
import com.alejandro.helphub.data.source.remote.repository.ProfileRepository
import com.alejandro.helphub.data.source.remote.server.response.ApiResponse
import com.alejandro.helphub.data.source.remote.server.response.ProfileResponse
import javax.inject.Inject

class FetchProfileUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke(): ApiResponse<ProfileResponse> {
        return try {
            profileRepository.fetchProfile()
        } catch (e: Exception) {
            Log.e("FetchProfileUseCase", "Error in request: ${e.message}")
            ApiResponse.Error(
                code = 500,
                message = e.message ?: "Unknown error occurred"
            )
        }
    }
}