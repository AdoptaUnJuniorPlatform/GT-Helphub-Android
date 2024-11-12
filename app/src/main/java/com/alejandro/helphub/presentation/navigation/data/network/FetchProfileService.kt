package com.alejandro.helphub.presentation.navigation.data.network

import android.util.Log
import com.alejandro.helphub.presentation.navigation.data.network.clients.FetchProfileClient
import com.alejandro.helphub.presentation.navigation.data.network.response.ApiResponse
import com.alejandro.helphub.presentation.navigation.data.network.response.ProfileResponse
import com.alejandro.helphub.presentation.navigation.data.network.response.UserId
import javax.inject.Inject

class FetchProfileService @Inject constructor(private val fetchProfileClient: FetchProfileClient) {
    suspend fun fetchProfile(): ApiResponse<ProfileResponse> {
        return try {
            val response = fetchProfileClient.fetchProfile()
            when {
                response.isSuccessful -> {
                    val profile = response.body()
                    if (profile==null) {
                        Log.i("FetchProfileService", "(null response)")
                        ApiResponse.Success(
                            ProfileResponse(
                                message = "Profile not found",
                                statusCode = 406,
                                id="",
                                userId = UserId(id = ""),
                                description = "",
                                interestedSkills = emptyList(),
                                location = "",
                                profilePicture = "",
                                preferredTimeRange = "",
                                selectedDays = emptyList()
                            )
                        )
                    } else {
                        Log.i("FetchProfileService", "${response.code()}+${response.message()}") //200+OK
                        ApiResponse.Success(profile)
                    }
                }
                else -> {
                    Log.e("FetchProfileService", "Error response: ${response.code()}+${response.message()}")
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