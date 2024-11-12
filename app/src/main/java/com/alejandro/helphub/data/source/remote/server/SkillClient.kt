package com.alejandro.helphub.data.source.remote.server

import com.alejandro.helphub.data.source.remote.dto.skill.CreateSkillDTO
import com.alejandro.helphub.data.source.remote.server.response.CreateSkillResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SkillClient {
    @POST("/api/helphub/hability")
    suspend fun createSkill(@Body createSkillDTO: com.alejandro.helphub.data.source.remote.dto.skill.CreateSkillDTO):Response<CreateSkillResponse>
}