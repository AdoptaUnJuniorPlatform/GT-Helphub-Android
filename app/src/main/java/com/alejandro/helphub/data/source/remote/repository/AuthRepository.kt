package com.alejandro.helphub.data.source.remote.repository

import android.util.Log
import com.alejandro.helphub.data.source.remote.mappers.UserDataMapper
import com.alejandro.helphub.data.source.remote.server.response.ProfileResponse
import com.alejandro.helphub.data.source.remote.server.response.UserId
import com.alejandro.helphub.data.source.remote.server.service.AuthService
import com.alejandro.helphub.domain.models.UserAuthData
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService: AuthService,
    private val userDataMapper:UserDataMapper,
    private val tokenRepository:TokenRepository
) {
    suspend fun getUserByEmail(email: String): Response<List<ProfileResponse>>{
    return try {
        val response = authService.getUserByEmail(email)

        // Retornamos directamente el Response de Retrofit
        if (response.isSuccessful) {
            response
        } else {
            // Si la respuesta no es exitosa, devolvemos un Response con un error
            Log.e("AuthRepository", "Failed with error code: ${response.code()}")
            Response.error(response.code(), response.errorBody() ?: "Unknown error".toResponseBody(
                null
            )
            )
        }
    } catch (e: Exception) {
        Log.e("AuthRepository", "Error fetching user: ${e.message}")
        // Retornamos un Response con error si ocurre una excepción
        Response.error(500,
            (e.message ?: "Unknown error").toResponseBody(null)
        )
    }
}

    suspend fun getUserById(userId: String): Response<ProfileResponse>{
        return try {
            val response = authService.getUserById(userId)

            // Retornamos directamente el Response de Retrofit
            if (response.isSuccessful) {
                response
            } else {
                // Si la respuesta no es exitosa, devolvemos un Response con un error
                Log.e("AuthRepository", "Failed with error code: ${response.code()}")
                Response.error(response.code(), response.errorBody() ?: "Unknown error".toResponseBody(
                    null
                )
                )
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error fetching user: ${e.message}")
            // Retornamos un Response con error si ocurre una excepción
            Response.error(500,
                (e.message ?: "Unknown error").toResponseBody(null)
            )
        }
    }


    suspend fun registerNewUser(userAuthData: UserAuthData): String {
        val userDTO = userDataMapper.toUserDTO(userAuthData)
        return authService.registerNewUser(userDTO)
    }

    suspend fun doLogin(userAuthData: UserAuthData): Result<String> {
        val loginDTO = userDataMapper.toLoginDTO(userAuthData)
        return authService.doLogin(loginDTO).map { loginResponse->
            val token=loginResponse.accessToken
            tokenRepository.saveToken(token)
            token
        }
    }

    suspend fun requestResetPassword(userAuthData: UserAuthData): Result<String> {
        val loginDTO = userDataMapper.requestResetPassword(userAuthData)
        return authService.requestResetPassword(loginDTO)
    }
}