package com.alejandro.helphub.data.source.remote.repository

import android.util.Log
import com.alejandro.helphub.domain.models.UserProfileData
import com.alejandro.helphub.data.source.remote.mappers.ProfileDataMapper
import com.alejandro.helphub.data.source.remote.server.response.ApiResponse
import com.alejandro.helphub.data.source.remote.server.response.ProfileResponse
import com.alejandro.helphub.data.source.remote.server.response.SearchResponse
import com.alejandro.helphub.data.source.remote.server.service.ProfileService
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

class ProfileRepository @Inject constructor(

    private val profileService: ProfileService,
    private val profileDataMapper: ProfileDataMapper
) {

    suspend fun getProfileById(id: String): Response<ProfileResponse> {
        return try {
            val response = profileService.getProfileById(id)

            // Retornamos directamente el Response de Retrofit
            if (response.isSuccessful) {
                response
            } else {
                // Si la respuesta no es exitosa, devolvemos un Response con un error
                Log.e("ProfileRepository", "Failed with error code: ${response.code()}")
                Response.error(response.code(), response.errorBody() ?: "Unknown error".toResponseBody(
                    null
                )
                )
            }
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Error fetching profile: ${e.message}")
            // Retornamos un Response con error si ocurre una excepción
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