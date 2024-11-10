package com.alejandro.helphub.features.profile.domain.mappers

import com.alejandro.helphub.features.profile.domain.CreateSkillDTO
import com.alejandro.helphub.features.profile.domain.SkillData
import javax.inject.Inject

class SkillDataMapper @Inject constructor(){
    fun createSkillDTO(skillData: SkillData):CreateSkillDTO{
        return CreateSkillDTO(
            title=skillData.title,
            level = skillData.level,
            mode = skillData.mode,
            description = skillData.description,
            category = skillData.category
        )
    }
}