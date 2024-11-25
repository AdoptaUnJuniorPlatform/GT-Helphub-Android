package com.alejandro.helphub.data.source.remote.repository


import android.util.Log
import com.alejandro.helphub.data.source.remote.dto.skill.CreateSkillDTO
import com.alejandro.helphub.data.source.remote.mappers.SkillDataMapper
import com.alejandro.helphub.data.source.remote.server.response.SkillResponse
import com.alejandro.helphub.data.source.remote.server.service.SkillService
import com.alejandro.helphub.domain.models.SkillData
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

class SkillRepository @Inject constructor(
    private val skillService: SkillService,
    private val skillDataMapper: SkillDataMapper
) {
    suspend fun getSkillsByCategory(category: String): Response<List<SkillResponse>> {
        return try {
            val response = skillService.getSkillsByCategory(category)
            if (response.isSuccessful) {
                response
            } else {
                Log.e(
                    "SkillRepository",
                    "Failed with error code: ${response.code()}"
                )
                Response.error(
                    response.code(),
                    response.errorBody() ?: "Unknown error".toResponseBody(
                        null
                    )
                )
            }
        } catch (e: Exception) {
            Log.e("SkillRepository", "Error fetching skill by category: ${e.message}")
            Response.error(
                500,
                (e.message ?: "Unknown error").toResponseBody(null)
            )
        }
    }

    suspend fun updateSkill(skillId: String,createSkillDTO: CreateSkillDTO):SkillData{
        val response = skillService.updateSkill(skillId, createSkillDTO)
        if (response.isSuccessful) {
            val skillResponse = response.body() ?: throw Exception("Response body is null")
            return skillDataMapper.mapToDomain(skillResponse)
        } else {
            throw Exception("Error updating skill: ${response.errorBody()?.string()}")
        }
    }

    suspend fun deleteSkill(skillId: String):Response<Unit> {
       return  skillService.deleteSkill(skillId)
    }

    suspend fun getSkillsByUserId(userId: String): Response<List<SkillResponse>> {
        return try {
            val response = skillService.getSkillsByUserId(userId)
            if (response.isSuccessful) {
                response
            } else {
                Log.e(
                    "SkillRepository",
                    "Failed with error code: ${response.code()}"
                )
                Response.error(
                    response.code(),
                    response.errorBody() ?: "Unknown error".toResponseBody(
                        null
                    )
                )
            }
        } catch (e: Exception) {
            Log.e("SkillRepository", "Error fetching skill: ${e.message}")
            Response.error(
                500,
                (e.message ?: "Unknown error").toResponseBody(null)
            )
        }
    }

    suspend fun createSkill(skillData: SkillData): String {
        val skillDTO = skillDataMapper.createSkillDTO(skillData)
        return skillService.createSkill(skillDTO)
    }
}