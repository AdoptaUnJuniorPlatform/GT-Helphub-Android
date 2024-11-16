package com.alejandro.helphub.data.source.remote.mappers

import com.alejandro.helphub.data.source.remote.dto.profile.CreateProfileDTO
import com.alejandro.helphub.data.source.remote.dto.profile.UploadProfileImageDTO
import com.alejandro.helphub.domain.models.ProfileImageData
import com.alejandro.helphub.domain.models.UserProfileData
import javax.inject.Inject

class ProfileDataMapper @Inject constructor() {
    fun createProfileDTO(userProfileData: UserProfileData): CreateProfileDTO {
        return CreateProfileDTO(
            location = userProfileData.location,
            description = userProfileData.description,
            //profilePicture = userProfileData.profilePicture,
            preferredTimeRange = userProfileData.preferredTimeRange,
            selectedDays = userProfileData.selectedDays,
            interestedSkills = userProfileData.interestedSkills
        )
    }
    fun uploadProfileImageDTO(profileImageData: ProfileImageData): UploadProfileImageDTO {
        return UploadProfileImageDTO(
            profileImage = profileImageData.imageProfile
        )
    }
}