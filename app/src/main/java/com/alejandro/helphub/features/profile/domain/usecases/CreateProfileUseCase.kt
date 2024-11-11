package com.alejandro.helphub.features.profile.domain.usecases

import com.alejandro.helphub.features.profile.data.ProfileRepository
import com.alejandro.helphub.features.profile.domain.UserProfileData
import javax.inject.Inject

class CreateProfileUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke (userProfileData: UserProfileData):String{
        return profileRepository.createProfile(userProfileData)
    }
}