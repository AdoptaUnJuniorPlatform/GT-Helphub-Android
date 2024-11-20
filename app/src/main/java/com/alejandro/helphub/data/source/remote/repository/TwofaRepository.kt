package com.alejandro.helphub.data.source.remote.repository


import com.alejandro.helphub.domain.models.UserAuthData
import com.alejandro.helphub.data.source.remote.mappers.UserDataMapper
import com.alejandro.helphub.data.source.remote.server.service.TwofaService
import javax.inject.Inject

class TwofaRepository @Inject constructor(
    private val twofaService: TwofaService,
    private val userDataMapper: com.alejandro.helphub.data.source.remote.mappers.UserDataMapper
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