package com.alejandro.helphub.features.auth.domain.mappers

import com.alejandro.helphub.features.auth.data.network.clients.UserDataDTO
import com.alejandro.helphub.features.auth.domain.UserData

object UserMapper {
    fun UserDataDTO.toDomain(): UserData {
        return UserData(
            email = email,
            nameUser = nameUser,
            surnameUser = surnameUser,
            phone = phone,
            optionCall = optionCall,
            showPhone = showPhone,
            blocked = blocked,
            // Set default values for other fields that aren't in the DTO
            surname2 = "",
            countryCode = "",
            twoFa = "",
            role = "user",
            userDescription = "",
            postalCode = "",
            // ... other fields with default values
        )
    }
}