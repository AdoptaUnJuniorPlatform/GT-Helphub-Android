package com.alejandro.helphub.data.source.remote.repository

import com.alejandro.helphub.domain.models.UserAuthData
import com.alejandro.helphub.data.source.remote.mappers.UserDataMapper
import com.alejandro.helphub.data.source.remote.server.service.AuthService
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService: AuthService,
    private val userDataMapper: com.alejandro.helphub.data.source.remote.mappers.UserDataMapper,
    private val tokenRepository: com.alejandro.helphub.data.source.remote.repository.TokenRepository
) {
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