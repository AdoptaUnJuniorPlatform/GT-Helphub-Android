package com.alejandro.helphub.domain.usecase.skill

import com.alejandro.helphub.data.source.remote.repository.SkillRepository
import com.alejandro.helphub.data.source.remote.server.response.SkillResponse
import retrofit2.Response
import javax.inject.Inject

class GetSkillsByUserIdUseCase @Inject constructor(private val skillRepository: SkillRepository){
    suspend operator fun invoke(userId:String): Response<List<SkillResponse>>{
        return skillRepository.getSkillsByUserId(userId)
    }
}