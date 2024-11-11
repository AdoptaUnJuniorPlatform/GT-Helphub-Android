package com.alejandro.helphub.features.profile.data.network

import com.alejandro.helphub.features.profile.data.network.clients.ProfileClient
import com.alejandro.helphub.features.profile.data.network.response.SearchResponse
import com.alejandro.helphub.features.profile.domain.CreateProfileDTO
import com.alejandro.helphub.features.profile.domain.CreateSkillDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class ProfileService @Inject constructor(private val profileClient: ProfileClient) {
    suspend fun getUserInfo(email: String): Response<SearchResponse> {
        return profileClient.getUserInfo(email)
    }

    suspend fun createProfile(createProfileDTO: CreateProfileDTO): String {
        return withContext(Dispatchers.IO) {
            val response = profileClient.createProfile(createProfileDTO)
            response.body()?.code ?: ""
        }
    }

}