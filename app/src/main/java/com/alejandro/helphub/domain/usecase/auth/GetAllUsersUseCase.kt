package com.alejandro.helphub.domain.usecase.auth

import com.alejandro.helphub.data.source.remote.repository.AuthRepository
import com.alejandro.helphub.data.source.remote.server.response.ProfileResponse
import retrofit2.Response
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke():Response<List<ProfileResponse>>{
        return authRepository.getAllUsers()
    }
}