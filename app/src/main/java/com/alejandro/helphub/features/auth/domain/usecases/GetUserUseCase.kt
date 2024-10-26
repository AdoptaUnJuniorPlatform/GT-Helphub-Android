package com.alejandro.helphub.features.auth.domain.usecases

import com.alejandro.helphub.features.auth.data.UserRepository
import com.alejandro.helphub.features.auth.domain.UserData
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(email:String): UserData?{
        return userRepository.getUserByEmail(email)
    }
}