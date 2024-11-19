package com.alejandro.helphub.domain.usecase.profile

import com.alejandro.helphub.data.source.remote.dto.profile.UpdatePfpDTO
import com.alejandro.helphub.data.source.remote.repository.ProfileRepository
import com.alejandro.helphub.data.source.remote.server.response.ProfileImageResponse
import com.alejandro.helphub.domain.models.UserProfileData
import okhttp3.MultipartBody
import javax.inject.Inject

class UpdateProfileImageUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke(id:String, idUserPart: MultipartBody.Part, imageProfilePart: MultipartBody.Part): ProfileImageResponse {
        return profileRepository.updateProfileImage(id,idUserPart,imageProfilePart)
    }
}