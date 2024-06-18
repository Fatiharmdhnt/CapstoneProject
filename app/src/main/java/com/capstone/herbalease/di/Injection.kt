package com.capstone.herbalease.di

import android.content.Context
import com.capstone.herbalease.data.model.retrofit.ApiService
import com.capstone.herbalease.data.pref.MainRepository
import com.capstone.herbalease.data.pref.UserPreference
import com.capstone.herbalease.data.pref.UserRepository
import com.capstone.herbalease.data.pref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Injection {
    private const val API_URL = "https://capstone-herbalease.uc.r.appspot.com/"

    private fun getApiService(token: String): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor { chain ->
                        val request = chain.request()
                        val authenticatedRequest =
                            if (token.isNotEmpty()) {
                                request.newBuilder()
                                    .addHeader("Authorization", "Bearer $token")
                                    .build()
                            } else {
                                request
                            }
                        chain.proceed(authenticatedRequest)
                    }
                    .build()
            )
            .build()
            .create(ApiService::class.java)
    }

    fun provideUserRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }

    fun provideMainRepository(context: Context): MainRepository {
        val token = runBlocking { provideUserRepository(context).getSession().first().token }
        return MainRepository(getApiService(token))
    }

    fun provideApiService(context: Context): ApiService {
        val token = runBlocking { provideUserRepository(context).getSession().first().token }
        return getApiService(token)
    }
}
