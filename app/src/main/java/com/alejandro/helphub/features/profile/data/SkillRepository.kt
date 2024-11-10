package com.alejandro.helphub.features.profile.data

import com.alejandro.helphub.features.profile.data.network.SkillService
import com.alejandro.helphub.features.profile.domain.SkillData
import com.alejandro.helphub.features.profile.domain.mappers.SkillDataMapper
import javax.inject.Inject

class SkillRepository @Inject constructor(
    private val skillService: SkillService,
    private val skillDataMapper: SkillDataMapper
){
    suspend fun createSkill(skillData: SkillData):String{
        val skillDTO=skillDataMapper.createSkillDTO(skillData)
        return skillService.createSkill(skillDTO)
}
}