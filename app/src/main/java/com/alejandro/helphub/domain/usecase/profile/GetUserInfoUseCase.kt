package com.alejandro.helphub.domain.usecase.profile

import com.alejandro.helphub.data.source.remote.repository.ProfileRepository
import com.alejandro.helphub.data.source.remote.server.response.SearchResponse
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke(email:String): Result<SearchResponse> {
        return profileRepository.getUserInfo(email)
    }
}