package com.alejandro.helphub.features.auth.domain.usecases

import com.alejandro.helphub.features.auth.data.TwofaRepository
import com.alejandro.helphub.features.auth.domain.UserData
import javax.inject.Inject

class SendTwoFaLoginUseCase @Inject constructor(private val twofaRepository: TwofaRepository){
    suspend operator fun invoke(userData: UserData):String{
        return twofaRepository.sendTwoFaLogin(userData)
    }
}