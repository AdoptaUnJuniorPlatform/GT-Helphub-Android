package com.alejandro.helphub.features.auth.data.network

import com.alejandro.helphub.features.auth.data.network.clients.InfoClient
import com.alejandro.helphub.features.auth.domain.UserData
import com.alejandro.helphub.features.auth.domain.mappers.UserMapper.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserService @Inject constructor(private val infoClient: InfoClient) {
    suspend fun getUserByEmail(email: String): UserData? {
        return withContext(Dispatchers.IO) {
            try {
                val response = infoClient.getUserByEmail(email)
                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                    response.body()?.first()?.toDomain()
                } else {
                    println("Error: ${response.errorBody()?.string()}")
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}