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
            //fix api call
            val searchedIngredientsResponse = apiService.searchTanaman("tanaman", query)
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
    fun getDiscussion() = liveData {
        emit(Result.Loading)
        try {
            val getDiscussionResponse = apiService.getDiscussion()
            emit(Result.Success(getDiscussionResponse))
        } catch (e : java.lang.Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

}