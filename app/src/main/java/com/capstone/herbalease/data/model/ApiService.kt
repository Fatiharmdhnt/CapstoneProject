package com.capstone.herbalease.data.model


import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    @POST("/register")
    suspend fun registerUser(@Body request: RegisterRequest): RegisterResponse

    @POST("/login")
    suspend fun loginUser(@Body request: LoginRequest): LoginResponse

    @GET("/profile")
    suspend fun getProfile(): ProfileResponse

    @Multipart
    @POST("/profile")
    suspend fun uploadProfile(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part file: MultipartBody.Part
    ): UploadResponse

    @Multipart
    @POST("/profile")
    suspend fun uploadProfileWithoutPhoto(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody
    ): UploadResponse

    @GET("/allHerbs")
    suspend fun getAllHerbs(): List<AppResponseItem>

    @GET("/allHerbs")
    suspend fun searchTanaman(
        @Query("kategori") kategori: String,
        @Query("value")value: String
    ) : List<AppResponseItem>
}