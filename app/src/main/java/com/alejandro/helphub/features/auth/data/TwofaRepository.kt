package com.alejandro.helphub.features.auth.data

import com.alejandro.helphub.features.auth.data.network.TwofaService
import com.alejandro.helphub.features.auth.domain.UserData
import javax.inject.Inject

class TwofaRepository @Inject constructor(private val twofaService: TwofaService){

    suspend fun twofaUser(userData: UserData):String{
        return twofaService.twofaUser(userData)
    }
}