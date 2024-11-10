package com.alejandro.helphub.features.profile.data.network.clients

import com.alejandro.helphub.features.profile.data.network.response.CreateSkillResponse
import com.alejandro.helphub.features.profile.domain.CreateSkillDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SkillClient {
    @POST("/api/helphub/hability")
    suspend fun createSkill(@Body createSkillDTO: CreateSkillDTO):Response<CreateSkillResponse>
}