package com.alejandro.helphub.presentation.navigation.data.network.response

sealed class ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error(
        val code: Int,
        val message: String
    ) : ApiResponse<Nothing>()
    object Loading : ApiResponse<Nothing>()
}