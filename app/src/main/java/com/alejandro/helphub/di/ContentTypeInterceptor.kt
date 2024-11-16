package com.alejandro.helphub.di

import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.Response
import javax.inject.Inject

class ContentTypeInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        // Verificar si la solicitud es multipart
        if (chain.request().method == "POST" && chain.request().url.pathSegments.contains("upload-profileImage")) {
            request.addHeader("Content-Type", "multipart/form-data")
        }

        return chain.proceed(request.build())
    }
}