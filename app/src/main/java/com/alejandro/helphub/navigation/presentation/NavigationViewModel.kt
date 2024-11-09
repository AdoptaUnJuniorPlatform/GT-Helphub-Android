package com.alejandro.helphub.navigation.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandro.helphub.navigation.data.network.response.ApiResponse
import com.alejandro.helphub.navigation.data.network.response.ProfileResponse
import com.alejandro.helphub.navigation.domain.ProfileUIState
import com.alejandro.helphub.navigation.domain.usecases.FetchProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val fetchProfileUseCase: FetchProfileUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProfileUIState>(ProfileUIState.Idle)
    val uiState: StateFlow<ProfileUIState> = _uiState.asStateFlow()

    fun fetchUserProfile() {
        Log.i("NavigationViewModel", "fetchUserProfile() called")
        viewModelScope.launch {
            try {
                _uiState.value = ProfileUIState.Loading
                when (val response = fetchProfileUseCase()) {
                    is ApiResponse.Success -> {
                        val statusCode = response.data.statusCode
                        Log.i("ProfileResponse", "Success: ${response.data.message}, Status: $statusCode")
                        _uiState.value = ProfileUIState.Success(statusCode)
                    }
                    is ApiResponse.Error -> {
                        Log.e("ProfileResponse", "Error: ${response.message}, Code: ${response.code}")
                        _uiState.value = ProfileUIState.Error(response.code)
                    }
                    is ApiResponse.Loading -> {
                        _uiState.value = ProfileUIState.Loading
                    }
                }
            } catch (e: Exception) {
                Log.e("ProfileResponse", "Exception: ${e.message}")
                _uiState.value = ProfileUIState.Error(500)
            }
        }
    }

    fun resetState() {
        _uiState.value = ProfileUIState.Idle
    }
}