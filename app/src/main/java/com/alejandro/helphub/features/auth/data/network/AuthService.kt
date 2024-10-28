package com.alejandro.helphub.features.auth.data.network

import com.alejandro.helphub.features.auth.data.network.clients.AuthClient
import com.alejandro.helphub.features.auth.domain.LoginDTO
import com.alejandro.helphub.features.auth.domain.UserDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthService @Inject constructor(private val authClient: AuthClient) {
    suspend fun registerNewUser(userDTO: UserDTO):String{
        return withContext(Dispatchers.IO){
            val response= authClient.registerNewUser(userDTO)
            response.body()?.code ?: "No code"
        }
    }

    suspend fun doLogin(loginDTO: LoginDTO):Result<String>{
        return withContext(Dispatchers.IO){
            val response=authClient.doLogin(loginDTO)
            if(response.isSuccessful) {
                response.body()?.access_token?.let { Result.success(it) }
                    ?: Result.failure(Exception("Token no disponible"))
            }else{Result.failure(Exception("Error en inicio de sesión"))}
        }
    }
}