package com.alejandro.helphub.features.auth.data

import com.alejandro.helphub.features.auth.data.network.UserService
import com.alejandro.helphub.features.auth.data.network.clients.InfoClient
import com.alejandro.helphub.features.auth.domain.UserData
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userService: UserService) {
    suspend fun getUserByEmail(email:String): UserData? {
        return userService.getUserByEmail(email)
    }
}