package com.alejandro.helphub.domain.models

import com.alejandro.helphub.data.source.remote.server.response.ProfileResponse

sealed class ProfileListUIState {
    object Idle : ProfileListUIState()
    object Loading : ProfileListUIState()
    object ProfileNotFound: ProfileListUIState()
    data class Success(val profile: List<ProfileResponse>) : ProfileListUIState()
    data class Error(val code: Int) : ProfileListUIState()
}
