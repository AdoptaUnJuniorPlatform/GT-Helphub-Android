package com.alejandro.helphub.data.source.remote.repository


import android.util.Log
import com.alejandro.helphub.domain.models.SkillData
import com.alejandro.helphub.data.source.remote.mappers.SkillDataMapper
import com.alejandro.helphub.data.source.remote.server.response.SkillResponse
import com.alejandro.helphub.data.source.remote.server.response.UserId
import com.alejandro.helphub.data.source.remote.server.service.SkillService
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

class SkillRepository @Inject constructor(
    private val skillService: SkillService,
    private val skillDataMapper: com.alejandro.helphub.data.source.remote.mappers.SkillDataMapper
) {
    suspend fun getSkillsByUserId(userId: String): Response<List<SkillResponse>> {
        return try {
            val response = skillService.getSkillsByUserId(userId)
            if (response.isSuccessful) {
                response
            } else {
                // Si la respuesta no es exitosa, devolvemos un Response con un error
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
            // Retornamos un Response con error si ocurre una excepción
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