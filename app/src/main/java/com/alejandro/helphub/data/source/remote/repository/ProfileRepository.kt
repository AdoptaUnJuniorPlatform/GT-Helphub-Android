package com.alejandro.helphub.data.source.remote.repository

import android.util.Log
import com.alejandro.helphub.data.source.remote.dto.profile.CreateProfileDTO
import com.alejandro.helphub.data.source.remote.mappers.ProfileDataMapper
import com.alejandro.helphub.data.source.remote.server.response.ApiResponse
import com.alejandro.helphub.data.source.remote.server.response.ProfileImageResponse
import com.alejandro.helphub.data.source.remote.server.response.ProfileResponse
import com.alejandro.helphub.data.source.remote.server.service.ProfileService
import com.alejandro.helphub.domain.models.UserProfileData
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

class ProfileRepository @Inject constructor(

    private val profileService: ProfileService,
    private val profileDataMapper: ProfileDataMapper
) {
    suspend fun getProfileImageByUserId(id:String):Response<ResponseBody> {
        return profileService.getProfileImageByUserId(id)
    }

    suspend fun getProfileByUserId(id: String): Response<ProfileResponse> {
        return try {
            val response = profileService.getProfileByUserId(id)

            if (response.isSuccessful) {
                response
            } else {
                Log.e("ProfileRepository", "Failed with error code: ${response.code()}")
                Response.error(response.code(), response.errorBody() ?: "Unknown error".toResponseBody(
                    null
                )
                )
            }
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Error fetching profile: ${e.message}")
            Response.error(500,
                (e.message ?: "Unknown error").toResponseBody(null)
            )
        }
    }

    suspend fun updateProfileImage(id:String,idUserPart: MultipartBody.Part, imageProfilePart: MultipartBody.Part): ProfileImageResponse {
        return profileService.updateProfileImage(id,idUserPart, imageProfilePart)
    }

    suspend fun updateProfile(id: String,createProfileDTO: CreateProfileDTO): UserProfileData {
        val response = profileService.updateProfile(id, createProfileDTO)
        if (response.isSuccessful) {
            val profileResponse = response.body() ?: throw Exception("Response body is null")
            return profileDataMapper.mapToDomain(profileResponse)
        } else {
            throw Exception("Error updating skill: ${response.errorBody()?.string()}")
        }
    }

    suspend fun getProfileImageByImageId(id: String): Response<ResponseBody> {
        return try {
            val response = profileService.getProfileImageByImageId(id)

            // Retornamos directamente el Response de Retrofit
            if (response.isSuccessful) {
                response
            } else {

                Log.e("ProfileRepository", "Failed with error code: ${response.code()}")
                Response.error(response.code(), response.errorBody() ?: "Unknown error".toResponseBody(
                    null
                )
                )
            }
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Error fetching profile image: ${e.message}")

            Response.error(500,
                (e.message ?: "Unknown error").toResponseBody(null)
            )
        }
    }
    suspend fun uploadProfileImage(idUserPart: MultipartBody.Part, imageProfilePart: MultipartBody.Part): ProfileImageResponse {
        return profileService.uploadProfileImage(idUserPart, imageProfilePart)
    }

    suspend fun getProfileById(id: String): Response<ProfileResponse> {
        return try {
            val response = profileService.getProfileById(id)

            if (response.isSuccessful) {
                response
            } else {
                Log.e("ProfileRepository", "Failed with error code: ${response.code()}")
                Response.error(response.code(), response.errorBody() ?: "Unknown error".toResponseBody(
                    null
                )
                )
            }
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Error fetching profile: ${e.message}")
            Response.error(500,
                (e.message ?: "Unknown error").toResponseBody(null)
            )
        }
    }

    suspend fun createProfile(userProfileData: UserProfileData): String {
        val createProfileDTO =
            profileDataMapper.createProfileDTO(userProfileData)
        return profileService.createProfile(createProfileDTO)
    }

    suspend fun fetchProfile(): ApiResponse<ProfileResponse> {
        return profileService.fetchProfile()
    }
}