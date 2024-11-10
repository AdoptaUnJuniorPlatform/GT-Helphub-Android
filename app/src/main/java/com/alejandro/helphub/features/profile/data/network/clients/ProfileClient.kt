package com.alejandro.helphub.features.profile.data.network.clients

import com.alejandro.helphub.features.profile.data.network.response.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileClient {
    @GET("/api/helphub/user/{email}")
    suspend fun getUserInfo(@Path("email")email:String):Response<SearchResponse>

//devuelve un array
}