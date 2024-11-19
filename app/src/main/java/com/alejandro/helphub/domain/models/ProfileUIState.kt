package com.alejandro.helphub.domain.models

import com.alejandro.helphub.data.source.remote.server.response.ProfileResponse

sealed class ProfileUIState {
    object Idle : ProfileUIState()
    object Loading : ProfileUIState()
    object ProfileNotFound: ProfileUIState()
    data class Success(val profile: ProfileResponse) : ProfileUIState()
    data class Error(val code: Int) : ProfileUIState()
}