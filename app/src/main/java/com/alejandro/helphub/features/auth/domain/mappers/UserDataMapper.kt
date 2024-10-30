package com.alejandro.helphub.features.auth.domain.mappers

import com.alejandro.helphub.features.auth.domain.LoginDTO
import com.alejandro.helphub.features.auth.domain.SendTwofaDTO
import com.alejandro.helphub.features.auth.domain.UserData
import com.alejandro.helphub.features.auth.domain.UserDTO
import javax.inject.Inject

class UserDataMapper @Inject constructor() {
    fun toUserDTO(userData: UserData): UserDTO {
        return UserDTO(
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
    }

    fun toLoginDTO(userData: UserData): LoginDTO {
        return LoginDTO(
            email = userData.email,
            password = userData.password
        )
    }

    fun requestResetPassword(userData: UserData): LoginDTO {
        return LoginDTO(
            email = userData.email,
            password = userData.password
        )
    }

    fun sendTwofaResetPasswordandLogin(userData: UserData): SendTwofaDTO {
        return SendTwofaDTO(
            email = userData.email,
            twoFa = userData.twoFa
        )
    }
}