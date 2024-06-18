package com.capstone.herbalease.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.herbalease.data.model.response.LoginRequest
import com.capstone.herbalease.data.pref.MainRepository
import com.capstone.herbalease.data.pref.UserModel
import com.capstone.herbalease.data.pref.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UserRepository,
    private val mainRepository: MainRepository
) : ViewModel() {
    fun login(loginData : LoginRequest) = mainRepository.login(loginData)

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}