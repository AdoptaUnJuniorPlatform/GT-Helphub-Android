package com.alejandro.helphub.data.source.remote.server.response

sealed class ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error(
        val code: Int,
        val message: String
    ) : ApiResponse<Nothing>()
    object Loading : ApiResponse<Nothing>()
}