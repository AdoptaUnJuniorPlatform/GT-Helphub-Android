package com.alejandro.helphub.data.source.remote.server.service

import com.alejandro.helphub.data.source.remote.dto.skill.CreateSkillDTO
import com.alejandro.helphub.data.source.remote.server.SkillClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SkillService @Inject constructor(private val skillClient: SkillClient) {
    suspend fun createSkill(createSkillDTO: com.alejandro.helphub.data.source.remote.dto.skill.CreateSkillDTO): String {
        return withContext(Dispatchers.IO) {
            val response = skillClient.createSkill(createSkillDTO)
            response.body()?.code ?: ""
        }
    }
}