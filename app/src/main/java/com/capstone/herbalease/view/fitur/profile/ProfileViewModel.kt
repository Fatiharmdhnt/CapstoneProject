package com.capstone.herbalease.view.fitur.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.herbalease.data.pref.MainRepository
import com.capstone.herbalease.data.pref.UserRepository
import kotlinx.coroutines.launch
import java.io.File

class ProfileViewModel(private val repository: UserRepository, private val mainRepository: MainRepository) : ViewModel() {

    fun getUser() = mainRepository.getProfile()

    fun updateData(name: String, email: String, file: File?) = mainRepository.updateProfile(name, email, file)


    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}
