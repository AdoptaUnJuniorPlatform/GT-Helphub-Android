package com.alejandro.helphub.features.profile.data

import com.alejandro.helphub.features.profile.data.network.ProfileService
import com.alejandro.helphub.features.profile.data.network.response.SearchResponse
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val profileService: ProfileService) {
    suspend fun getUserInfo(email: String): Result<SearchResponse> {
        return try {
            val response = profileService.getUserInfo(email)
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) } ?: Result.failure(
                    Exception("Response body is null")
                )
            } else {
                Result.failure(Exception("Failed with error code : ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}