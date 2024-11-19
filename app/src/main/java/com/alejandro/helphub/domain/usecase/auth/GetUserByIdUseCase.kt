package com.alejandro.helphub.domain.usecase.auth

import com.alejandro.helphub.data.source.remote.repository.AuthRepository
import com.alejandro.helphub.data.source.remote.server.response.ProfileResponse
import com.alejandro.helphub.data.source.remote.server.response.UserId
import retrofit2.Response
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(userId:String): Response<ProfileResponse> {
        return authRepository.getUserById(userId)
    }
}