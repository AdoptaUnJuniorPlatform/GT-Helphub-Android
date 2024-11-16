package com.alejandro.helphub.data.source.remote.server

import com.alejandro.helphub.data.source.remote.dto.skill.CreateSkillDTO
import com.alejandro.helphub.data.source.remote.server.response.CreateSkillResponse
import com.alejandro.helphub.data.source.remote.server.response.SkillResponse
import com.alejandro.helphub.data.source.remote.server.response.UserId
import com.alejandro.helphub.domain.models.SkillData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SkillClient {
    @POST("/api/helphub/hability")
    suspend fun createSkill(@Body createSkillDTO: CreateSkillDTO): Response<CreateSkillResponse>

    @GET("/api/helphub/hability/user-habilities/{id}")
    suspend fun getSkillsByUserId(@Path("id") userId: String): Response<List<SkillResponse>>

}