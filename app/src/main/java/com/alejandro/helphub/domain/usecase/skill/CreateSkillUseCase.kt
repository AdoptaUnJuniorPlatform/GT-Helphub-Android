package com.alejandro.helphub.domain.usecase.skill

import com.alejandro.helphub.data.source.remote.repository.SkillRepository
import com.alejandro.helphub.domain.models.SkillData
import javax.inject.Inject

class CreateSkillUseCase @Inject constructor(private val skillRepository: SkillRepository) {
    suspend operator fun invoke(skillData: SkillData):String{
        return skillRepository.createSkill(skillData)
    }
}