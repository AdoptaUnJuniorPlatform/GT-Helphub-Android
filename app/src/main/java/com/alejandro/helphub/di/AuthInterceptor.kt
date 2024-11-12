package com.alejandro.helphub.di

import android.util.Log
import com.alejandro.helphub.data.source.remote.repository.TokenRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val tokenRepository: TokenRepository) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenRepository.getToken()
        Log.i("AuthInterceptor", "Using token: $token")
        val request = chain.request().newBuilder()
            .addHeader("Authorization","Bearer $token")
            .build()
        return chain.proceed(request)
    }
}