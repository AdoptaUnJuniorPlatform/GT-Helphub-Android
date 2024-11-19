package com.alejandro.helphub.domain.usecase.skill

import com.alejandro.helphub.data.source.remote.dto.skill.CreateSkillDTO
import com.alejandro.helphub.data.source.remote.repository.SkillRepository
import com.alejandro.helphub.domain.models.SkillData
import javax.inject.Inject

class UpdateSkillUseCase @Inject constructor(private val skillRepository: SkillRepository) {
    suspend operator fun invoke(skillId:String,createSkillDTO: CreateSkillDTO):SkillData{
        return skillRepository.updateSkill(skillId, createSkillDTO)
    }
}