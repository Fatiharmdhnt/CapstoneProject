package com.capstone.herbalease.data.pref

import androidx.lifecycle.liveData
import com.capstone.herbalease.data.model.retrofit.ApiService
import com.capstone.herbalease.data.model.response.LoginRequest
import com.capstone.herbalease.data.model.response.RegisterRequest

import com.capstone.herbalease.di.Result
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.File

class MainRepository(
    private val apiService: ApiService,
) {
    fun login(loginData : LoginRequest) = liveData {
        emit(Result.Loading)
        try {
            val loginResponse = apiService.loginUser(loginData)
            emit(Result.Success(loginResponse))
        }catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMsg = try {
                val jsonObject = JSONObject(errorJsonString)
                jsonObject.getString("msg")
            } catch (e: JSONException) {
                e.message.toString()
            }
            emit(Result.Error(errorMsg))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }

    fun register(
        registerData : RegisterRequest
    ) = liveData {
        emit(Result.Loading)
        try {
            val registerResponse = apiService.registerUser(registerData)
            emit(Result.Success(registerResponse))
        }catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMsg = try {
                val jsonObject = JSONObject(errorJsonString)
                jsonObject.getString("msg")
            } catch (e: JSONException) {
                e.message.toString()
            }
            emit(Result.Error(errorMsg))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getProfile() = liveData {
        emit(Result.Loading)
        try {
            val profileResponse = apiService.getProfile()
            emit(Result.Success(profileResponse))
        }catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMsg = try {
                val jsonObject = JSONObject(errorJsonString)
                jsonObject.getString("msg")
            } catch (e: JSONException) {
                e.message.toString()
            }
            emit(Result.Error(errorMsg))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }

    fun updateProfile(name: String, email: String, file: File?) = liveData {
        emit(Result.Loading)
        try {
            val nameRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
            val emailRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), email)
            if(file!=null){
                val fileRequestBody = file.asRequestBody("image/png".toMediaTypeOrNull())
                val filePart = MultipartBody.Part.createFormData("file", file.name, fileRequestBody)

                val profileResponse = apiService.uploadProfile(nameRequestBody, emailRequestBody, filePart)
                emit(Result.Success(profileResponse))
            }else{
                val profileResponse = apiService.uploadProfileWithoutPhoto(nameRequestBody, emailRequestBody)
                emit(Result.Success(profileResponse))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }
}