package com.alejandro.helphub.domain.usecase.auth.sendtwofa


import com.alejandro.helphub.data.source.remote.repository.TwofaRepository
import com.alejandro.helphub.domain.models.UserAuthData
import javax.inject.Inject

class SendTwoFaResetPasswordUseCase @Inject constructor(private val twofaRepository: TwofaRepository) {
    suspend operator fun invoke(userAuthData: UserAuthData): String {
        return twofaRepository.sendTwoFaResetPassword(userAuthData)
    }
}