package com.capstone.herbalease.view.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.capstone.herbalease.data.model.AppResponseItem
import com.capstone.herbalease.data.pref.AppRepository
import com.capstone.herbalease.di.Result
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: AppRepository) : ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _searchedIngredients = MutableLiveData<List<AppResponseItem>>()
    val searchedIngredients: LiveData<List<AppResponseItem>> = _searchedIngredients

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        loadInitialIngredients()
    }

    fun loadInitialIngredients() {
        viewModelScope.launch {
            repository.getIngredientsList().asFlow().collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _isLoading.postValue(true)
                    }
                    is Result.Success -> {
                        _isLoading.postValue(false)
                        _searchedIngredients.postValue(result.data)
                    }
                    is Result.Error -> {
                        _isLoading.value = false
                        _errorMessage.value = result.error
                    }
                }
            }
        }
    }

    fun searchIngredients(query: String, isFromSearch: Boolean) {
        viewModelScope.launch {
            val response = if (isFromSearch) {
                repository.searchKeluhanIngredients(query)
            } else {
                repository.searchIngredients(query)
            }

            response.asFlow().collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _isLoading.postValue(true)
                    }
                    is Result.Success -> {
                        _isLoading.postValue(false)
                        _searchedIngredients.postValue(result.data)
                    }
                    is Result.Error -> {
                        _isLoading.value = false
                        _errorMessage.value = result.error
                    }
                }
            }
        }
    }
}
