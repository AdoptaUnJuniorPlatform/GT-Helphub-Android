package com.alejandro.helphub.features.profile.domain.usecases

import com.alejandro.helphub.features.auth.domain.UserAuthData
import com.alejandro.helphub.features.profile.data.ProfileRepository
import com.alejandro.helphub.features.profile.data.network.response.SearchResponse
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke(email:String): Result<SearchResponse> {
        return profileRepository.getUserInfo(email)
    }
}