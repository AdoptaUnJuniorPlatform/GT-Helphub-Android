package com.alejandro.helphub.features.auth.data.network

import com.alejandro.helphub.features.auth.data.network.clients.TwofaClient
import com.alejandro.helphub.features.auth.domain.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TwofaService @Inject constructor(private val twofaClient: TwofaClient){

    suspend fun twofaUser(userData: UserData):String{
        return withContext(Dispatchers.IO){
           val response= twofaClient.twofaUser(userData)
            response.body()?.code ?: "No code"
        }
    }
}