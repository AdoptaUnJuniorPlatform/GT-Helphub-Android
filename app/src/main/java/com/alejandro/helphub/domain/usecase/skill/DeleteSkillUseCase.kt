package com.alejandro.helphub.domain.usecase.skill

import com.alejandro.helphub.data.source.remote.repository.SkillRepository
import retrofit2.Response
import javax.inject.Inject

class DeleteSkillUseCase @Inject constructor(private val skillRepository: SkillRepository) {
    suspend operator fun invoke(skillId: String): Response<Unit> {
        return  skillRepository.deleteSkill(skillId)
    }
}