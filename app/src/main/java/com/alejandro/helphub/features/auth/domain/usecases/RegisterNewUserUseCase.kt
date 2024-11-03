package com.alejandro.helphub.features.auth.domain.usecases

import com.alejandro.helphub.features.auth.data.AuthRepository
import com.alejandro.helphub.features.auth.domain.UserAuthData
import javax.inject.Inject

class RegisterNewUserUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(userAuthData: UserAuthData): String {
        return authRepository.registerNewUser(userAuthData)
    }
}
