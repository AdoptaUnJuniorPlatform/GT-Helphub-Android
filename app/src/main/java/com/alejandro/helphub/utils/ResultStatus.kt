package com.alejandro.helphub.utils

sealed class ResultStatus<out T> {
    data class Success<out T>(val data: T) : ResultStatus<T>()
    data class Error(val message: String) : ResultStatus<Nothing>()
    object Idle : ResultStatus<Nothing>()
}