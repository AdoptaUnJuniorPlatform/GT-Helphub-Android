package com.alejandro.helphub.data.source.remote.mappers

import com.alejandro.helphub.data.source.remote.dto.profile.CreateProfileDTO
import com.alejandro.helphub.data.source.remote.server.response.ProfileResponse
import com.alejandro.helphub.domain.models.UserProfileData
import javax.inject.Inject

class ProfileDataMapper @Inject constructor() {
    fun createProfileDTO(userProfileData: UserProfileData): CreateProfileDTO {
        return CreateProfileDTO(
            location = userProfileData.location,
            description = userProfileData.description,
            preferredTimeRange = userProfileData.preferredTimeRange,
            selectedDays = userProfileData.selectedDays,
            interestedSkills = userProfileData.interestedSkills
        )
    }

    fun mapToDomain(profileResponse: ProfileResponse): UserProfileData {
        return UserProfileData(
            location = profileResponse.location,
            description = profileResponse.description,
            profileImage = profileResponse.profilePicture,
            preferredTimeRange = profileResponse.preferredTimeRange,
            selectedDays = profileResponse.selectedDays,
            interestedSkills = profileResponse.interestedSkills
        )
    }
}