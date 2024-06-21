package com.capstone.herbalease.data.model.retrofit

import android.content.Context
import com.capstone.herbalease.data.pref.UserPreference
import com.capstone.herbalease.data.pref.dataStore
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(context: Context): ApiService {
            val userPreference = UserPreference.getInstance(context.dataStore)

            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val token = runBlocking { userPreference.getToken() }
                    val original = chain.request()
                    val requestBuilder = original.newBuilder()
                        .header("Authorization", "Bearer $token")
                    val request = requestBuilder.build()
                    chain.proceed(request)
                }
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://frontend-dot-capstone-herbalease.uc.r.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}