package com.alejandro.helphub.navigation.data.network

import android.util.Log
import com.alejandro.helphub.navigation.data.network.clients.FetchProfileClient
import com.alejandro.helphub.navigation.data.network.response.ApiResponse
import com.alejandro.helphub.navigation.data.network.response.ProfileResponse
import retrofit2.Response
import javax.inject.Inject

class FetchProfileService @Inject constructor(private val fetchProfileClient: FetchProfileClient) {
    suspend fun fetchProfile(): ApiResponse<ProfileResponse> {
        return try {
            val response = fetchProfileClient.fetchProfile()
            when {
                response.isSuccessful -> {
                    val profileList = response.body()
                    when {
                        profileList.isNullOrEmpty() -> {
                            // Handle empty response as "Profile not found"
                            Log.i("FetchProfileService", "No profile found")
                            ApiResponse.Success(
                                ProfileResponse(
                                    message = "Profile not found",
                                    statusCode = 404
                                )
                            )
                        }

                        else -> {
                            val profile = profileList[0]
                            Log.i("FetchProfileService", "Profile found: ${profile.message}")
                            ApiResponse.Success(profile)
                        }
                    }
                }
                else -> {
                    Log.e("FetchProfileService", "Error response: ${response.code()}")
                    ApiResponse.Error(
                        code = response.code(),
                        message = response.message()
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("FetchProfileService", "Exception occurred: ${e.message}")
            ApiResponse.Error(
                code = 500,
                message = e.message ?: "Unknown error occurred"
            )
        }
    }
}