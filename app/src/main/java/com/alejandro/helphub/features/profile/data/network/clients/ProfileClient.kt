package com.alejandro.helphub.features.profile.data.network.clients

import com.alejandro.helphub.features.profile.data.network.response.CreateSkillResponse
import com.alejandro.helphub.features.profile.data.network.response.SearchResponse
import com.alejandro.helphub.features.profile.domain.CreateProfileDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProfileClient {
    @GET("/api/helphub/user/{email}")
    suspend fun getUserInfo(@Path("email")email:String):Response<SearchResponse>

    @POST("/api/helphub/profile")
    suspend fun createProfile(@Body createProfileDTO: CreateProfileDTO):Response<CreateSkillResponse>
}