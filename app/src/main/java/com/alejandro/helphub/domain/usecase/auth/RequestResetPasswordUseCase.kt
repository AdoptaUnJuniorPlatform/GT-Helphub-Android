package com.alejandro.helphub.domain.usecase.auth


import com.alejandro.helphub.data.source.remote.repository.AuthRepository
import com.alejandro.helphub.domain.models.UserAuthData
import javax.inject.Inject

class RequestResetPasswordUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(userAuthData: UserAuthData): Result<String> {
        return authRepository.requestResetPassword(userAuthData)
    }
}