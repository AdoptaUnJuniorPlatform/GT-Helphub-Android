package com.alejandro.helphub.features.auth.data

import com.alejandro.helphub.features.auth.data.network.AuthService
import com.alejandro.helphub.features.auth.domain.UserData
import com.alejandro.helphub.features.auth.domain.mappers.UserDataMapper
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService: AuthService,
    private val userDataMapper: UserDataMapper
) {
    suspend fun registerNewUser(userData: UserData): String {
        val userDTO = userDataMapper.toUserDTO(userData)
        return authService.registerNewUser(userDTO)
    }

    suspend fun doLogin(userData: UserData): Result<String> {
        val loginDTO = userDataMapper.toLoginDTO(userData)
        return authService.doLogin(loginDTO)
    }

    suspend fun requestResetPassword(userData: UserData): Result<String> {
        val loginDTO = userDataMapper.requestResetPassword(userData)
        return authService.requestResetPassword(loginDTO)
    }
}