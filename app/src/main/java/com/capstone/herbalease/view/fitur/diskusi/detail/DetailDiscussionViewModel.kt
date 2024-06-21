package com.capstone.herbalease.view.fitur.diskusi.detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.capstone.herbalease.data.model.response.discussion.PostCommentResponse
import com.capstone.herbalease.data.model.retrofit.ApiConfig
import com.capstone.herbalease.data.model.retrofit.Komen
import com.capstone.herbalease.data.pref.Comments
import com.capstone.herbalease.data.pref.ForumDiscussion
import com.capstone.herbalease.data.pref.AppRepository
import com.capstone.herbalease.data.pref.UserModel
import com.capstone.herbalease.data.pref.UserRepository
import com.capstone.herbalease.di.Result
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailDiscussionViewModel(private val repository: UserRepository) : ViewModel(){

    private val _listComment = MutableLiveData<List<Comments>?>()
    val listComment : LiveData<List<Comments>?> get() = _listComment

    private val _userSession = MutableLiveData<UserModel>()
    val userSession : LiveData<UserModel> get() = _userSession

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        viewModelScope.launch {
            repository.getSession().collect { session ->
                _userSession.postValue(session)
            }
        }
    }

    fun sendComment(context : Context, comment : String, data : ForumDiscussion){

        _userSession.value?.let {
            ApiConfig.getApiService(context).postComment(
                data.id,
                it.id,
                Komen(comment)
            ).enqueue(object : Callback<PostCommentResponse>{
                override fun onResponse(
                    call: Call<PostCommentResponse>,
                    response: Response<PostCommentResponse>
                ) {
                    if (response != null){
                        val newComment = Comments(
                            response.body()?.comment?.name.toString(),
                            _userSession.value!!.profilePicture,
                            comment
                        )

                        // Safely update the comments list
                        val updatedComments = data.comments?.toMutableList() ?: mutableListOf()
                        updatedComments.add(newComment)
                        _listComment.postValue(updatedComments)
                    }
                }

                override fun onFailure(call: Call<PostCommentResponse>, t: Throwable) {
                    Log.e("Error", "Failed Add Comment")
                }

            })
        }
    }
}