package com.alejandro.helphub.domain.usecase.profile

import com.alejandro.helphub.data.source.remote.repository.ProfileRepository
import com.alejandro.helphub.data.source.remote.server.response.ProfileImageResponse
import okhttp3.MultipartBody
import javax.inject.Inject

class UploadProfileImageUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke(idUserPart: MultipartBody.Part, imageProfilePart: MultipartBody.Part):ProfileImageResponse{
        return profileRepository.uploadProfileImage(idUserPart, imageProfilePart)
    }
}