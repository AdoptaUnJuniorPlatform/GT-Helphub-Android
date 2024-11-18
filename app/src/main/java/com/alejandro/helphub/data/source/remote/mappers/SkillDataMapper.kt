package com.alejandro.helphub.data.source.remote.mappers

import com.alejandro.helphub.domain.models.SkillData
import com.alejandro.helphub.data.source.remote.dto.skill.CreateSkillDTO
import com.alejandro.helphub.data.source.remote.server.response.SkillResponse
import javax.inject.Inject

class SkillDataMapper @Inject constructor(){
    fun createSkillDTO(skillData: SkillData):CreateSkillDTO {
        return CreateSkillDTO(
            title = skillData.title,
            level = skillData.level,
            mode = skillData.mode,
            description = skillData.description,
            category = skillData.category
        )
    }
    fun mapToDomain(skillResponse: SkillResponse): SkillData {
        return SkillData(
            id = skillResponse.id,
            title = skillResponse.title,
            level = skillResponse.level,
            mode = skillResponse.mode,
            description = skillResponse.description,
            category = skillResponse.category,
            userId = skillResponse.userId
        )
    }
}