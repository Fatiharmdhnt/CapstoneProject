package com.capstone.herbalease.data.model.response

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("loginResult")
    val loginResult: LoginResult
)

data class LoginResult(
    @field:SerializedName("userId")
    val userId: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("token")
    val token: String,

    @field:SerializedName("profile_picture_url")
    val photoProfile : String
)