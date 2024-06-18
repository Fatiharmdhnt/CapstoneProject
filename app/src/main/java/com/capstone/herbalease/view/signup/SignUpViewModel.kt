package com.capstone.herbalease.view.signup

import androidx.lifecycle.ViewModel
import com.capstone.herbalease.data.model.response.RegisterRequest
import com.capstone.herbalease.data.pref.MainRepository

class SignupViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {
    fun register(registerData : RegisterRequest) =
        mainRepository.register(registerData)
}