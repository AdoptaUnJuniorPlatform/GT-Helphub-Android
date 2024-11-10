package com.alejandro.helphub.features.profile.data.network

import com.alejandro.helphub.features.profile.data.network.clients.SkillClient
import com.alejandro.helphub.features.profile.data.network.response.CreateSkillResponse
import com.alejandro.helphub.features.profile.domain.CreateSkillDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class SkillService @Inject constructor(private val skillClient: SkillClient) {
    suspend fun createSkill(createSkillDTO: CreateSkillDTO): String {
        return withContext(Dispatchers.IO) {
            val response = skillClient.createSkill(createSkillDTO)
            response.body()?.code ?: ""
        }
    }
}