package com.alejandro.helphub.data.source.remote.repository


import com.alejandro.helphub.domain.models.SkillData
import com.alejandro.helphub.data.source.remote.mappers.SkillDataMapper
import com.alejandro.helphub.data.source.remote.server.service.SkillService
import javax.inject.Inject

class SkillRepository @Inject constructor(
    private val skillService: SkillService,
    private val skillDataMapper: com.alejandro.helphub.data.source.remote.mappers.SkillDataMapper
){
    suspend fun createSkill(skillData: SkillData):String{
        val skillDTO=skillDataMapper.createSkillDTO(skillData)
        return skillService.createSkill(skillDTO)
}
}