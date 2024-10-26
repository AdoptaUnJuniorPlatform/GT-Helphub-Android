package com.alejandro.helphub.features.auth.data.network

import com.alejandro.helphub.features.auth.data.network.clients.TwofaClient
import com.alejandro.helphub.features.auth.domain.TwofaDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TwofaService @Inject constructor(private val twofaClient: TwofaClient){

    suspend fun sendTwoFaRegister(twofaDTO: TwofaDTO):String{
        return withContext(Dispatchers.IO){
           val response= twofaClient.sendTwoFaRegister(twofaDTO)
            response.body()?.code ?: "No code"
        }
    }
}