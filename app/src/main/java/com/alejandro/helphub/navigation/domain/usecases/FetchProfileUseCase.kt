package com.alejandro.helphub.navigation.domain.usecases

import com.alejandro.helphub.navigation.data.network.FetchProfileRepository
import com.alejandro.helphub.navigation.data.network.response.ProfileResponse
import retrofit2.Response
import javax.inject.Inject

class FetchProfileUseCase @Inject constructor(private val fetchProfileRepository: FetchProfileRepository) {
suspend operator fun invoke(): Response<ProfileResponse> {
    return fetchProfileRepository.fetchProfile()
}
}