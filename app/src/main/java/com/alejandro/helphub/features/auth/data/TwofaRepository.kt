package com.alejandro.helphub.features.auth.data

import com.alejandro.helphub.features.auth.data.network.TwofaService
import com.alejandro.helphub.features.auth.domain.TwofaDTO
import com.alejandro.helphub.features.auth.domain.UserData
import javax.inject.Inject

class TwofaRepository @Inject constructor(private val twofaService: TwofaService){

    suspend fun sendTwoFaRegister(userData: UserData): String {
        val twofaDTO= TwofaDTO(
            email = userData.email,
            twoFa = userData.twoFa,
            password = userData.password,
            nameUser = userData.nameUser,
            surnameUser = userData.surnameUser,
            phone = userData.phone,
            optionCall = userData.optionCall,
            showPhone = userData.showPhone,
            blocked = userData.blocked,
            role = userData.role
        )
        return twofaService.sendTwoFaRegister(twofaDTO)
    }
}