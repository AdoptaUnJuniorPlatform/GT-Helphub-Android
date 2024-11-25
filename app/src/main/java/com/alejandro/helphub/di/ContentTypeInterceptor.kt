package com.alejandro.helphub.di

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ContentTypeInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        // Verify if the call is multipart
        if (chain.request().method == "POST" && chain.request().url.pathSegments.contains("upload-profileImage")) {
            request.addHeader("Content-Type", "multipart/form-data")
        }
        return chain.proceed(request.build())
    }
}