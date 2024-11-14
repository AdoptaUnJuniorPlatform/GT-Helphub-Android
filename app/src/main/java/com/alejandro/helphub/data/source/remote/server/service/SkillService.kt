package com.alejandro.helphub.data.source.remote.server.service

import com.alejandro.helphub.data.source.remote.dto.skill.CreateSkillDTO
import com.alejandro.helphub.data.source.remote.server.SkillClient
import com.alejandro.helphub.data.source.remote.server.response.SkillResponse
import com.alejandro.helphub.data.source.remote.server.response.UserId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class SkillService @Inject constructor(private val skillClient: SkillClient) {
    suspend fun createSkill(createSkillDTO:CreateSkillDTO): String {
        return withContext(Dispatchers.IO) {
            val response = skillClient.createSkill(createSkillDTO)
            response.body()?.code ?: ""
        }
    }
    suspend fun getSkillsByUserId(userId: String):Response<List<SkillResponse>>{
        return skillClient.getSkillsByUserId(userId)
    }
}