package com.alejandro.helphub.features.auth.data.network

import com.alejandro.helphub.features.auth.data.network.clients.TwofaClient
import com.alejandro.helphub.features.auth.domain.LoginDTO
import com.alejandro.helphub.features.auth.domain.UserDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TwofaService @Inject constructor(private val twofaClient: TwofaClient) {

    suspend fun sendTwoFaRegister(userDTO: UserDTO): String {
        return withContext(Dispatchers.IO) {
            val response = twofaClient.sendTwoFaRegister(userDTO)
            response.body()?.code ?: "No code"
        }}

        suspend fun sendTwoFaResetPassword(loginDTO: LoginDTO): String {
            return withContext(Dispatchers.IO) {
                val response = twofaClient.sendTwoFaResetPassword(loginDTO)
                response.body()?.code ?: "No code"
            }
        }
    }