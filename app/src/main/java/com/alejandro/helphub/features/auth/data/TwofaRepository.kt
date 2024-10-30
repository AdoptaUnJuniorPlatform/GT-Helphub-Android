package com.alejandro.helphub.features.auth.data

import com.alejandro.helphub.features.auth.data.network.TwofaService
import com.alejandro.helphub.features.auth.domain.UserData
import com.alejandro.helphub.features.auth.domain.mappers.UserDataMapper
import javax.inject.Inject

class TwofaRepository @Inject constructor(private val twofaService: TwofaService, private val userDataMapper: UserDataMapper) {

    suspend fun sendTwoFaRegister(userData: UserData): String {
        val twofaDTO = userDataMapper.toUserDTO(userData)
        return twofaService.sendTwoFaRegister(twofaDTO)
    }
    suspend fun sendTwoFaResetPassword(userData: UserData): String {
        val twofaDTO = userDataMapper.sendTwofaResetPasswordandLogin(userData)
        return twofaService.sendTwoFaResetPassword(twofaDTO)
    }
    suspend fun sendTwoFaLogin(userData: UserData):String{
        val twofaDTO=userDataMapper.sendTwofaResetPasswordandLogin(userData)
        return twofaService.sendTwofaLogin(twofaDTO)
    }
}