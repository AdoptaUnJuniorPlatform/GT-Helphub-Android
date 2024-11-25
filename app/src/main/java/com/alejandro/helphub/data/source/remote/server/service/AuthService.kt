package com.alejandro.helphub.data.source.remote.server.service

import com.alejandro.helphub.data.source.remote.dto.auth.LoginDTO
import com.alejandro.helphub.data.source.remote.dto.auth.UserDTO
import com.alejandro.helphub.data.source.remote.server.AuthClient
import com.alejandro.helphub.data.source.remote.server.response.LoginResponse
import com.alejandro.helphub.data.source.remote.server.response.ProfileResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class AuthService @Inject constructor(private val authClient: AuthClient) {

    suspend fun getAllUsers(): Response<List<ProfileResponse>> {
        return authClient.getAllUsers()
    }

    suspend fun getUserByEmail(email: String): Response<List<ProfileResponse>> {
        return authClient.getUserByEmail(email)
    }


    suspend fun getUserById(userId: String): Response<ProfileResponse> {
        return authClient.getUserById(userId)
    }

    suspend fun registerNewUser(userDTO: UserDTO): String {
        return withContext(Dispatchers.IO) {
            val response = authClient.registerNewUser(userDTO)
            response.body()?.code ?: "No code"
        }
    }

    suspend fun doLogin(loginDTO: LoginDTO): Result<LoginResponse> {
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

    suspend fun requestResetPassword(loginDTO: LoginDTO): Result<String> {
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