package com.alejandro.helphub.features.auth.data

import com.alejandro.helphub.features.auth.data.network.AuthService
import com.alejandro.helphub.features.auth.domain.UserAuthData
import com.alejandro.helphub.features.auth.domain.mappers.UserDataMapper
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService: AuthService,
    private val userDataMapper: UserDataMapper
) {
    suspend fun registerNewUser(userAuthData: UserAuthData): String {
        val userDTO = userDataMapper.toUserDTO(userAuthData)
        return authService.registerNewUser(userDTO)
    }

    suspend fun doLogin(userAuthData: UserAuthData): Result<String> {
        val loginDTO = userDataMapper.toLoginDTO(userAuthData)
        return authService.doLogin(loginDTO)
    }

    suspend fun requestResetPassword(userAuthData: UserAuthData): Result<String> {
        val loginDTO = userDataMapper.requestResetPassword(userAuthData)
        return authService.requestResetPassword(loginDTO)
    }
}