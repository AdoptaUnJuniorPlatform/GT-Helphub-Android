package com.alejandro.helphub.data.source.remote.mappers

import com.alejandro.helphub.data.source.remote.dto.profile.CreateProfileDTO
import com.alejandro.helphub.domain.models.UserProfileData
import javax.inject.Inject

class ProfileDataMapper @Inject constructor() {
    fun createProfileDTO(userProfileData: UserProfileData): com.alejandro.helphub.data.source.remote.dto.profile.CreateProfileDTO {
        return com.alejandro.helphub.data.source.remote.dto.profile.CreateProfileDTO(
            location = userProfileData.location,
            description = userProfileData.description,
            profilePicture = userProfileData.profilePicture,
            preferredTimeRange = userProfileData.preferredTimeRange,
            selectedDays = userProfileData.selectedDays,
            interestedSkills = userProfileData.interestedSkills
        )
    }
}