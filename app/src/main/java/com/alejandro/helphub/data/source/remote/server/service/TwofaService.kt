package com.alejandro.helphub.data.source.remote.server.service

import com.alejandro.helphub.data.source.remote.server.TwofaClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TwofaService @Inject constructor(private val twofaClient: TwofaClient) {

    suspend fun sendTwoFaRegister(userDTO: com.alejandro.helphub.data.source.remote.dto.auth.UserDTO): String {
        return withContext(Dispatchers.IO) {
            val response = twofaClient.sendTwoFaRegister(userDTO)
            response.body()?.code ?: "No code"
        }
    }

    suspend fun sendTwoFaResetPassword(sendTwofaDTO: com.alejandro.helphub.data.source.remote.dto.auth.SendTwofaDTO): String {
        return withContext(Dispatchers.IO) {
            val response = twofaClient.sendTwoFaResetPassword(sendTwofaDTO)
            response.body()?.code ?: "No code"
        }
    }

    suspend fun sendTwofaLogin(sendTwofaDTO: com.alejandro.helphub.data.source.remote.dto.auth.SendTwofaDTO): String {
        return withContext(Dispatchers.IO) {
            val response = twofaClient.sendTwoFaLogin(sendTwofaDTO)
            response.body()?.code ?: "No code"
        }
    }
}