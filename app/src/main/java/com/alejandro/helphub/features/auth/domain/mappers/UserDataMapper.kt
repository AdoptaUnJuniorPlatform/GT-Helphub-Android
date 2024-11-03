package com.alejandro.helphub.features.auth.domain.mappers

import com.alejandro.helphub.features.auth.domain.LoginDTO
import com.alejandro.helphub.features.auth.domain.SendTwofaDTO
import com.alejandro.helphub.features.auth.domain.UserAuthData
import com.alejandro.helphub.features.auth.domain.UserDTO
import javax.inject.Inject

class UserDataMapper @Inject constructor() {
    fun toUserDTO(userAuthData: UserAuthData): UserDTO {
        return UserDTO(
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

    fun toLoginDTO(userAuthData: UserAuthData): LoginDTO {
        return LoginDTO(
            email = userAuthData.email,
            password = userAuthData.password
        )
    }

    fun requestResetPassword(userAuthData: UserAuthData): LoginDTO {
        return LoginDTO(
            email = userAuthData.email,
            password = userAuthData.password
        )
    }

    fun sendTwofaResetPasswordandLogin(userAuthData: UserAuthData): SendTwofaDTO {
        return SendTwofaDTO(
            email = userAuthData.email,
            twoFa = userAuthData.twoFa
        )
    }
}