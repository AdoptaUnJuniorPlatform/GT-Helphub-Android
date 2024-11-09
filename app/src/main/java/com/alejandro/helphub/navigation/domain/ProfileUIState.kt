package com.alejandro.helphub.navigation.domain

sealed class ProfileUIState {
    object Idle : ProfileUIState()
    object Loading : ProfileUIState()
    data class Success(val statusCode: Int) : ProfileUIState()
    data class Error(val code: Int) : ProfileUIState()
}