package com.capstone.herbalease.view.welcome

import androidx.lifecycle.*
import com.capstone.herbalease.data.pref.UserModel
import com.capstone.herbalease.data.pref.UserRepository
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _userSession = MutableLiveData<UserModel>()
    val userSession: LiveData<UserModel> get() = _userSession

    fun getSession() {
        viewModelScope.launch {
            repository.getSession().collect { user ->
                _userSession.postValue(user)
            }
        }
    }


}
