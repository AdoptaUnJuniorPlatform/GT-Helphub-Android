package com.alejandro.helphub.domain.usecase.profile

import com.alejandro.helphub.data.source.remote.repository.ProfileRepository
import com.alejandro.helphub.domain.models.UserProfileData
import javax.inject.Inject

class CreateProfileUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke (userProfileData: UserProfileData):String{
        return profileRepository.createProfile(userProfileData)
    }
}