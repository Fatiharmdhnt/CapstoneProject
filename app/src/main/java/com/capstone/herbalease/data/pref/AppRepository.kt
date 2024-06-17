package com.capstone.herbalease.data.pref

import androidx.lifecycle.liveData
import com.capstone.herbalease.data.model.ApiService
import com.capstone.herbalease.di.Result

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
}