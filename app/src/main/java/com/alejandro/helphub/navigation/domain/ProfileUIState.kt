package com.alejandro.helphub.navigation.domain

import com.alejandro.helphub.navigation.data.network.response.ProfileResponse

sealed class ProfileUIState {
    object Idle : ProfileUIState()
    object Loading : ProfileUIState()
    object ProfileNotFound:ProfileUIState()
    data class Success(val profile:ProfileResponse) : ProfileUIState()
    data class Error(val code: Int) : ProfileUIState()
}