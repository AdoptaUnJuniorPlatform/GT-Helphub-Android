package com.alejandro.helphub.features.profile.domain.mappers

import com.alejandro.helphub.features.profile.domain.CreateProfileDTO
import com.alejandro.helphub.features.profile.domain.UserProfileData
import javax.inject.Inject

class ProfileDataMapper @Inject constructor() {
    fun createProfileDTO(userProfileData: UserProfileData):CreateProfileDTO{
        return CreateProfileDTO(
            location = userProfileData.location,
            description = userProfileData.description,
            profilePicture = userProfileData.profilePicture,
            preferredTimeRange = userProfileData.preferredTimeRange,
            selectedDays = userProfileData.selectedDays,
            interestedSkills = userProfileData.interestedSkills
        )
    }
}