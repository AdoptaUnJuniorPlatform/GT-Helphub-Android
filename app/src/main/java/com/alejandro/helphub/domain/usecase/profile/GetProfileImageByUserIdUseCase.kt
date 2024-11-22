package com.alejandro.helphub.domain.usecase.profile

import com.alejandro.helphub.data.source.remote.repository.ProfileRepository
import com.alejandro.helphub.data.source.remote.server.response.ProfileResponse
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class GetProfileImageByUserIdUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke(id:String): Response<ResponseBody> {
        return profileRepository.getProfileImageByUserId(id)
    }
}