package com.alejandro.helphub.features.auth.domain.usecases

import com.alejandro.helphub.features.auth.data.AuthRepository
import com.alejandro.helphub.features.auth.domain.UserData
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(userData: UserData):Result<String>{
        return authRepository.doLogin(userData)
    }
}