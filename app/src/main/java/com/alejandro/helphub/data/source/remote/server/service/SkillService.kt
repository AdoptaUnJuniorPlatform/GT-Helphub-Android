package com.alejandro.helphub.data.source.remote.server.service

import com.alejandro.helphub.data.source.remote.dto.skill.CreateSkillDTO
import com.alejandro.helphub.data.source.remote.server.SkillClient
import com.alejandro.helphub.data.source.remote.server.response.SkillResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class SkillService @Inject constructor(private val skillClient: SkillClient) {

    suspend fun getSkillsByCategory(category: String):Response<List<SkillResponse>>{
        return skillClient.getSkillsByCategory(category)
    }

    suspend fun updateSkill(skillId:String, createSkillDTO: CreateSkillDTO):Response<SkillResponse>{
        return skillClient.updateSkill(skillId,createSkillDTO)
    }

    suspend fun deleteSkill(skillId:String):Response<Unit>{
         return skillClient.deleteSkill(skillId)
    }

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