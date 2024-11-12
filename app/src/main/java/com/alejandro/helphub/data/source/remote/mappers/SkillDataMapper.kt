package com.alejandro.helphub.data.source.remote.mappers

import com.alejandro.helphub.domain.models.SkillData
import com.alejandro.helphub.data.source.remote.dto.skill.CreateSkillDTO
import javax.inject.Inject

class SkillDataMapper @Inject constructor(){
    fun createSkillDTO(skillData: SkillData): com.alejandro.helphub.data.source.remote.dto.skill.CreateSkillDTO {
        return com.alejandro.helphub.data.source.remote.dto.skill.CreateSkillDTO(
            title = skillData.title,
            level = skillData.level,
            mode = skillData.mode,
            description = skillData.description,
            category = skillData.category
        )
    }
}