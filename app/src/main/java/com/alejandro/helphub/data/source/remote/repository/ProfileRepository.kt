package com.alejandro.helphub.data.source.remote.repository

import com.alejandro.helphub.domain.models.UserProfileData
import com.alejandro.helphub.data.source.remote.mappers.ProfileDataMapper
import com.alejandro.helphub.data.source.remote.server.response.ApiResponse
import com.alejandro.helphub.data.source.remote.server.response.ProfileResponse
import com.alejandro.helphub.data.source.remote.server.response.SearchResponse
import com.alejandro.helphub.data.source.remote.server.service.ProfileService
import javax.inject.Inject

class ProfileRepository @Inject constructor(

    private val profileService: ProfileService,
    private val profileDataMapper: ProfileDataMapper
) {
    suspend fun createProfile(userProfileData: UserProfileData): String {
        val createProfileDTO =
            profileDataMapper.createProfileDTO(userProfileData)
        return profileService.createProfile(createProfileDTO)
    }


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
    suspend fun fetchProfile(): ApiResponse<ProfileResponse> {
        return profileService.fetchProfile()
    }

}