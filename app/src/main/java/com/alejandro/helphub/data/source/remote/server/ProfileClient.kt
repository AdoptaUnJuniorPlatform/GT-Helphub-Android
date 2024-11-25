package com.alejandro.helphub.data.source.remote.server


import com.alejandro.helphub.data.source.remote.dto.profile.CreateProfileDTO
import com.alejandro.helphub.data.source.remote.server.response.CreateSkillResponse
import com.alejandro.helphub.data.source.remote.server.response.ProfileImageResponse
import com.alejandro.helphub.data.source.remote.server.response.ProfileResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ProfileClient {

    @POST("/api/helphub/profile")
    suspend fun createProfile(@Body createProfileDTO: CreateProfileDTO): Response<CreateSkillResponse>

    @GET("/api/helphub/profile")
    suspend fun fetchProfile(): Response<ProfileResponse>

    @GET("/api/helphub/profile/{id}")
    suspend fun getProfileById(@Path("id") id: String): Response<ProfileResponse>

    @Multipart
    @POST("/api/helphub/upload-service/upload-profileImage")
    suspend fun uploadProfileImage(
        @Part id_user: MultipartBody.Part,
        @Part image_profile: MultipartBody.Part
    ): Response<ProfileImageResponse>


    @GET("/api/helphub/upload-service/profile-image/{id}")
    suspend fun getProfileImageByImageId(@Path("id") id: String): Response<ResponseBody>

    @PATCH("/api/helphub/profile/{id}")
    suspend fun updateProfile(@Path("id")id:String, @Body createProfileDTO: CreateProfileDTO):Response<ProfileResponse>

    @Multipart
    @PATCH("/api/helphub/upload-service/profile-image-user/{id}")
    suspend fun updateProfileImageByUserId(
        @Path("id")id:String,
        @Part id_user: MultipartBody.Part,
        @Part image_profile: MultipartBody.Part):Response<ProfileImageResponse>

    @GET("/api/helphub/profile/byUserId/{id}")
    suspend fun getProfileByUserId(@Path("id") id: String): Response<ProfileResponse>

    @GET("/api/helphub/upload-service/profile-imageByUser/{id}")
    suspend fun getProfileImageByUserId(@Path("id")id:String):Response<ResponseBody>
}