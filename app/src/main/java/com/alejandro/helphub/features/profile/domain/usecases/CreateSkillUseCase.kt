package com.alejandro.helphub.features.profile.domain.usecases

import com.alejandro.helphub.features.profile.data.SkillRepository
import com.alejandro.helphub.features.profile.domain.SkillData
import javax.inject.Inject

class CreateSkillUseCase @Inject constructor(private val skillRepository: SkillRepository) {
    suspend operator fun invoke(skillData: SkillData):String{
        return skillRepository.createSkill(skillData)
    }
}