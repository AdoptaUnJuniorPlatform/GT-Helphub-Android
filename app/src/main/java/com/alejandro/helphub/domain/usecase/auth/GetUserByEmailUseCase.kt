package com.alejandro.helphub.domain.usecase.auth

import com.alejandro.helphub.data.source.remote.repository.AuthRepository
import com.alejandro.helphub.data.source.remote.server.response.ProfileResponse
import retrofit2.Response
import javax.inject.Inject

class GetUserByEmailUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email:String): Response<List<ProfileResponse>>{
        return authRepository.getUserByEmail(email)
    }
}