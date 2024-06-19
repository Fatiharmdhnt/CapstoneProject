package com.capstone.herbalease.data.pref

import androidx.lifecycle.liveData
import com.capstone.herbalease.data.model.retrofit.ApiService
import com.capstone.herbalease.di.Result
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AppRepository(
    private val apiService: ApiService,
) {
    fun getHeadlineIngredientsList() = liveData {
        emit(Result.Loading)
        try {
            val ingredientsResponse = apiService.getAllHerbs().take(10)
            emit(Result.Success(ingredientsResponse))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getIngredientsList() = liveData {
        emit(Result.Loading)
        try {
            val ingredientsResponse = apiService.getAllHerbs()
            emit(Result.Success(ingredientsResponse))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun searchIngredients(query: String) = liveData {
        emit(Result.Loading)
        try {
            val searchedIngredientsResponse = apiService.searchTanaman("Tanaman", query)
            emit(Result.Success(searchedIngredientsResponse))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun searchKeluhanIngredients(query: String) = liveData {
        emit(Result.Loading)
        try {
            val searchedIngredientsResponse = apiService.searchTanaman("keluhan", query)
            emit(Result.Success(searchedIngredientsResponse))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }


    //DISCUSSION FUNCTION
    fun getDiscussion(id : Int) = liveData {
        emit(Result.Loading)
        try {
            val getDiscussionResponse = apiService.getDiscussion(id)
            emit(Result.Success(getDiscussionResponse))
        } catch (e : java.lang.Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    fun postDiscussion(
        title : RequestBody,
        photoDiscussion : MultipartBody.Part,
        description : RequestBody,
        keyword : RequestBody
    ) = liveData{
        emit(Result.Loading)
        try {
            val postDiscussionResponse = apiService.postDiscussion(title, photoDiscussion, description, keyword)
            emit(Result.Success(postDiscussionResponse))
        } catch (e : Exception){
            emit(Result.Error(e.message.toString()))
        }
    }
}