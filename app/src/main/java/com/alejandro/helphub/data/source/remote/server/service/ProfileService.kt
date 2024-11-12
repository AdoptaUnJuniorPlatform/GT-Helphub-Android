package com.alejandro.helphub.data.source.remote.server.service


import com.alejandro.helphub.data.source.remote.dto.profile.CreateProfileDTO
import com.alejandro.helphub.data.source.remote.server.ProfileClient
import com.alejandro.helphub.data.source.remote.server.response.SearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class ProfileService @Inject constructor(private val profileClient: ProfileClient) {
    suspend fun getUserInfo(email: String): Response<SearchResponse> {
        return profileClient.getUserInfo(email)
    }

    suspend fun createProfile(createProfileDTO: com.alejandro.helphub.data.source.remote.dto.profile.CreateProfileDTO): String {
        return withContext(Dispatchers.IO) {
            val response = profileClient.createProfile(createProfileDTO)
            response.body()?.code ?: ""
        }
    }

}