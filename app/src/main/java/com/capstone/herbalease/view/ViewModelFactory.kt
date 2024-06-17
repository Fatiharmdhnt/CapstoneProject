package com.capstone.herbalease.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.herbalease.data.pref.AppRepository
import com.capstone.herbalease.data.pref.MainRepository
import com.capstone.herbalease.data.pref.UserRepository
import com.capstone.herbalease.di.Injection
import com.capstone.herbalease.view.fitur.home.HomeViewModel
import com.capstone.herbalease.view.fitur.profile.ProfileViewModel
import com.capstone.herbalease.view.login.LoginViewModel
import com.capstone.herbalease.view.search.SearchViewModel
import com.capstone.herbalease.view.signup.SignupViewModel
import com.capstone.herbalease.view.welcome.WelcomeViewModel


class ViewModelFactory(context: Context) : ViewModelProvider.NewInstanceFactory() {

    private val userRepository: UserRepository = Injection.provideUserRepository(context)
    private val mainRepository: MainRepository = Injection.provideMainRepository(context)
    private val apiService = Injection.provideApiService(context)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository, mainRepository) as T
            }

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(userRepository, mainRepository) as T
            }

            modelClass.isAssignableFrom(WelcomeViewModel::class.java) -> {
                WelcomeViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(mainRepository) as T
            }

            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                val appRepository = AppRepository(apiService)
                HomeViewModel(appRepository, mainRepository) as T
            }

            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                val appRepository = AppRepository(apiService)
                SearchViewModel(appRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}
