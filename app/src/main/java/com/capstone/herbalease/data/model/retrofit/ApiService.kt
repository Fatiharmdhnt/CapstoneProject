package com.capstone.herbalease.data.model.retrofit

import com.capstone.herbalease.data.model.response.AppResponseItem
import com.capstone.herbalease.data.model.response.LoginRequest
import com.capstone.herbalease.data.model.response.LoginResponse
import com.capstone.herbalease.data.model.response.ProfileResponse
import com.capstone.herbalease.data.model.response.RegisterRequest
import com.capstone.herbalease.data.model.response.RegisterResponse
import com.capstone.herbalease.data.model.response.UploadResponse
import com.capstone.herbalease.data.model.response.discussion.GetDiscussionResponseItem
import com.capstone.herbalease.data.model.response.discussion.PostCommentResponse
import com.capstone.herbalease.data.model.response.discussion.PostDiscussionResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("register")
    suspend fun registerUser(@Body request: RegisterRequest): RegisterResponse

    @POST("login")
    suspend fun loginUser(@Body request: LoginRequest): LoginResponse

    @GET("profile")
    suspend fun getProfile(): ProfileResponse

    @Multipart
    @POST("profile")
    suspend fun uploadProfile(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part file: MultipartBody.Part
    ): UploadResponse

    @Multipart
    @POST("profile")
    suspend fun uploadProfileWithoutPhoto(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody
    ): UploadResponse

    @GET("allHerbs")
    suspend fun getAllHerbs(): List<AppResponseItem>

    //fix api call
    @GET("/search")
    suspend fun searchTanaman(
        @Query("kategori") kategori: String,
        @Query("value")value: String
    ) : List<AppResponseItem>

    @GET("forum-discussion/{id}")
    fun getDiscussion(
        @Path("id") id : Int
    ): List<GetDiscussionResponseItem>

    @Multipart
    @POST("forum-discussion")
    fun postDiscussion(
        @Part("title") title: RequestBody,
        @Part photoDiscussionUrl: MultipartBody.Part,
        @Part("description") description: RequestBody?,
        @Part("keyword") keyword: RequestBody?
    ) : PostDiscussionResponse

    @FormUrlEncoded
    @POST("forum-discussion/{id}/comment")
    fun postComment(
        @Path("id") id : Int,
        @Field("forumDiscussionId") discId : Int,
        @Field("comment") comment : String
    ) : PostCommentResponse

    @DELETE("forum-discussion/{id}")
    fun deleteDiscussion(
        @Path("id") id: Int
    ) : String
}