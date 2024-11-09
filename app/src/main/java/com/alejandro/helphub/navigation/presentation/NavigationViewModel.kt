package com.alejandro.helphub.navigation.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandro.helphub.navigation.data.network.response.ProfileResponse
import com.alejandro.helphub.navigation.domain.usecases.FetchProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject
@HiltViewModel
class NavigationViewModel @Inject constructor(private val fetchProfileUseCase: FetchProfileUseCase) :
    ViewModel() {
    private val _profileStatusCode = MutableStateFlow<Int?>(null)
    val profileStatusCode: StateFlow<Int?> = _profileStatusCode
    fun fetchUserProfile() {
        viewModelScope.launch {
            val response= fetchProfileUseCase()
            if (response.isSuccessful) {
                _profileStatusCode.value = response.code() // Status code 200, etc.
                Log.i("ProfileResponse", "Response Succesful Status Code: ${response.code()}")
            } else {
                _profileStatusCode.value = response.code() // O puedes manejar el error
                Log.i("ProfileResponse", "Response Fail Status Code: ${response.code()}")
            }
        }
    }
}