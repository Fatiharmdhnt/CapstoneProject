package com.capstone.herbalease.view.fitur.diskusi.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.capstone.herbalease.data.pref.Comments
import com.capstone.herbalease.data.pref.ForumDiscussion
import com.capstone.herbalease.data.pref.AppRepository
import com.capstone.herbalease.data.pref.UserModel
import com.capstone.herbalease.data.pref.UserRepository
import com.capstone.herbalease.di.Result
import kotlinx.coroutines.launch

class DetailDiscussionViewModel(private val repository: UserRepository, private val appRepository: AppRepository) : ViewModel(){

    private val _listComment = MutableLiveData<List<Comments>?>()
    val listComment : LiveData<List<Comments>?> get() = _listComment

    private val _userSession = MutableLiveData<UserModel>()
    val userSession : LiveData<UserModel> get() = _userSession

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getSession(){
        viewModelScope.launch {
            repository.getSession().collect { session ->
                _userSession.postValue(session)
            }
        }
    }

    fun sendComment(comment : String, data : ForumDiscussion){

        viewModelScope.launch {
            val response = _userSession.value?.let { appRepository.postComment(it.id, data.id, comment) }

            if (response != null) {
                response.asFlow().collect{ result ->
                    when (result) {
                        is Result.Loading-> {
                            _isLoading.postValue(true)
                        }
                        is Result.Success ->{
                            _isLoading.postValue(false)
                            val newComment = Comments(
                                    result.data.comment?.name.toString(),
                            data.photoProfileUrl,
                            comment
                            )

                            // Safely update the comments list
                            val updatedComments = data.comments?.toMutableList() ?: mutableListOf()
                            updatedComments.add(newComment)
                            _listComment.postValue(updatedComments)
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
}