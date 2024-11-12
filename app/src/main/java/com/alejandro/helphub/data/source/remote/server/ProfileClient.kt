package com.alejandro.helphub.data.source.remote.server


import com.alejandro.helphub.data.source.remote.server.response.CreateSkillResponse
import com.alejandro.helphub.data.source.remote.server.response.SearchResponse
import com.alejandro.helphub.data.source.remote.dto.profile.CreateProfileDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProfileClient {
    @GET("/api/helphub/user/{email}")
    suspend fun getUserInfo(@Path("email")email:String):Response<SearchResponse>

    @POST("/api/helphub/profile")
    suspend fun createProfile(@Body createProfileDTO:CreateProfileDTO ):Response<CreateSkillResponse>


}