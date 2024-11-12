package com.alejandro.helphub.data.source.remote.server.service

import com.alejandro.helphub.data.source.remote.dto.auth.LoginDTO
import com.alejandro.helphub.data.source.remote.dto.auth.UserDTO
import com.alejandro.helphub.data.source.remote.server.AuthClient
import com.alejandro.helphub.data.source.remote.server.response.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthService @Inject constructor(private val authClient: AuthClient) {
    suspend fun registerNewUser(userDTO: com.alejandro.helphub.data.source.remote.dto.auth.UserDTO): String {
        return withContext(Dispatchers.IO) {
            val response = authClient.registerNewUser(userDTO)
            response.body()?.code ?: "No code"
        }
    }

    suspend fun doLogin(loginDTO: com.alejandro.helphub.data.source.remote.dto.auth.LoginDTO): Result<LoginResponse> {
        return withContext(Dispatchers.IO) {
            val response = authClient.doLogin(loginDTO)
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) }
                    ?: Result.failure(Exception("Token no disponible"))
            } else {
                Result.failure(
                    Exception(
                        "Error en inicio de sesión${
                            response.errorBody()?.string()
                        }"
                    )
                )
            }
        }
    }

    suspend fun requestResetPassword(loginDTO: com.alejandro.helphub.data.source.remote.dto.auth.LoginDTO): Result<String> {
        return withContext(Dispatchers.IO) {
            val response = authClient.resetPasswordRequest(loginDTO)
            if (response.isSuccessful) {
                response.body()?.code?.let { Result.success(it) }
                    ?: Result.failure(Exception("Nueva contraseña no aceptada"))
            } else {
                Result.failure(
                    Exception(
                        "Error en cambio de contraseña${
                            response.errorBody()?.string()
                        }"
                    )
                )
            }
        }
    }
}