package com.alejandro.helphub.features.auth.data

import com.alejandro.helphub.features.auth.data.network.TwofaService
import com.alejandro.helphub.features.auth.domain.UserAuthData
import com.alejandro.helphub.features.auth.domain.mappers.UserDataMapper
import javax.inject.Inject

class TwofaRepository @Inject constructor(
    private val twofaService: TwofaService,
    private val userDataMapper: UserDataMapper
) {

    suspend fun sendTwoFaRegister(userAuthData: UserAuthData): String {
        val twofaDTO = userDataMapper.toUserDTO(userAuthData)
        return twofaService.sendTwoFaRegister(twofaDTO)
    }

    suspend fun sendTwoFaResetPassword(userAuthData: UserAuthData): String {
        val twofaDTO = userDataMapper.sendTwofaResetPasswordandLogin(userAuthData)
        return twofaService.sendTwoFaResetPassword(twofaDTO)
    }

    suspend fun sendTwoFaLogin(userAuthData: UserAuthData): String {
        val twofaDTO = userDataMapper.sendTwofaResetPasswordandLogin(userAuthData)
        return twofaService.sendTwofaLogin(twofaDTO)
    }
}