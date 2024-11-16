package com.alejandro.helphub.data.source.remote.mappers

import com.alejandro.helphub.domain.models.UserAuthData
import com.alejandro.helphub.data.source.remote.dto.auth.LoginDTO
import com.alejandro.helphub.data.source.remote.dto.auth.SendTwofaDTO
import com.alejandro.helphub.data.source.remote.dto.auth.UserDTO
import javax.inject.Inject

class UserDataMapper @Inject constructor() {
    fun toUserDTO(userAuthData: UserAuthData): com.alejandro.helphub.data.source.remote.dto.auth.UserDTO {
        return com.alejandro.helphub.data.source.remote.dto.auth.UserDTO(
            email = userAuthData.email,
            twoFa = userAuthData.twoFa,
            password = userAuthData.password,
            nameUser = userAuthData.nameUser,
            surnameUser = userAuthData.surnameUser,
            phone = userAuthData.phone,
            optionCall = userAuthData.optionCall,
            showPhone = userAuthData.showPhone,
            blocked = userAuthData.blocked,
            role = userAuthData.role
        )
    }

    fun toLoginDTO(userAuthData: UserAuthData): com.alejandro.helphub.data.source.remote.dto.auth.LoginDTO {
        return com.alejandro.helphub.data.source.remote.dto.auth.LoginDTO(
            email = userAuthData.email,
            password = userAuthData.password
        )
    }

    fun requestResetPassword(userAuthData: UserAuthData): com.alejandro.helphub.data.source.remote.dto.auth.LoginDTO {
        return com.alejandro.helphub.data.source.remote.dto.auth.LoginDTO(
            email = userAuthData.email,
            password = userAuthData.password
        )
    }

    fun sendTwofaResetPasswordandLogin(userAuthData: UserAuthData): com.alejandro.helphub.data.source.remote.dto.auth.SendTwofaDTO {
        return com.alejandro.helphub.data.source.remote.dto.auth.SendTwofaDTO(
            email = userAuthData.email,
            twoFa = userAuthData.twoFa
        )
    }
}