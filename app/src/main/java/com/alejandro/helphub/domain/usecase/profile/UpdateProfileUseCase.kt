package com.alejandro.helphub.domain.usecase.profile

import com.alejandro.helphub.data.source.remote.dto.profile.CreateProfileDTO
import com.alejandro.helphub.data.source.remote.repository.ProfileRepository
import com.alejandro.helphub.domain.models.UserProfileData
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend operator  fun invoke(id:String, createProfileDTO: CreateProfileDTO):UserProfileData{
        return profileRepository.updateProfile(id, createProfileDTO)
    }
}