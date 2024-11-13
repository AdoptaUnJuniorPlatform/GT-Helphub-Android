package com.alejandro.helphub.domain.usecase.profile

import com.alejandro.helphub.data.source.remote.repository.ProfileRepository
import com.alejandro.helphub.data.source.remote.server.response.ProfileResponse
import retrofit2.Response
import javax.inject.Inject

class GetProfileByIdUseCase @Inject constructor(private val profileRepository: ProfileRepository){
    suspend operator fun invoke(id:String): Response<ProfileResponse> {
        return profileRepository.getProfileById(id)
    }
}