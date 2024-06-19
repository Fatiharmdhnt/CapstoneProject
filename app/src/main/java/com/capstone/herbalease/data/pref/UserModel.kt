package com.capstone.herbalease.data.pref

data class UserModel(
    val email: String,
    val id: Int,
    val token: String,
    val isLogin: Boolean = false
)