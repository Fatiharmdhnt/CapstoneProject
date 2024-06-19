package com.capstone.herbalease.view.fitur.diskusi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.capstone.herbalease.data.pref.Comments
import com.capstone.herbalease.data.pref.ForumDiscussion
import com.capstone.herbalease.data.pref.Keyword
import com.capstone.herbalease.data.model.response.discussion.GetDiscussionResponseItem
import com.capstone.herbalease.data.pref.AppRepository
import com.capstone.herbalease.data.pref.UserModel
import com.capstone.herbalease.data.pref.UserRepository
import com.capstone.herbalease.di.Result
import kotlinx.coroutines.launch

class DiscussionViewModel(private val repository: UserRepository, private val appRepository: AppRepository) : ViewModel() {

    private val _listDiscussion = MutableLiveData<List<ForumDiscussion>?>()
    val listDiscussion: LiveData<List<ForumDiscussion>?> get() = _listDiscussion
    private val _userSession = MutableLiveData<UserModel>()
    val userSession: LiveData<UserModel> get() = _userSession

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
    fun setDiscussion() {
        val id = _userSession.value!!.id
        println( _userSession.value!!.id)
        viewModelScope.launch {
            val response = appRepository.getDiscussion(_userSession.value!!.id)

            response.asFlow().collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _isLoading.postValue(true)
                    }
                    is Result.Success -> {
                        _isLoading.postValue(false)
                        _listDiscussion.postValue(dicussionResponseToForumDiscussion(result.data))
                    }
                    is Result.Error -> {
                        _isLoading.value = false
                        _errorMessage.value = result.error
                    }
                }
            }
        }
    }

    fun searchDiscussion(query: String?) {
        if (query != null) {
            val currentList = _listDiscussion.value ?: listOf()
            val filteredList = currentList.filter { discussion ->
                discussion.title.contains(query, ignoreCase = true) ||
                        discussion.keyword.any { keyword ->
                            keyword.keyword.contains(query, ignoreCase = true)
                        }
            }
            _listDiscussion.postValue(filteredList)
        } else {
            setDiscussion()
        }
    }

    fun dicussionResponseToForumDiscussion(response: List<GetDiscussionResponseItem>): MutableList<ForumDiscussion> {
        val result: MutableList<ForumDiscussion> = mutableListOf()
        response.forEach { item ->
            val discuss = ForumDiscussion(
                id = item.id ?: 0, // Provide a default value or handle the null case
                name = item.name.toString(),
                title = item.title.toString(),
                photoProfileUrl = item.photoProfileUrl.toString(),
                description = item.description.toString(),
                photoDiscussionUrl = item.photoDiscussionUrl.toString(),
                keyword = mutableListOf(),
                comments = mutableListOf()
            )

            // Keyword
            val keyword = item.keyword.toString().split(", ")
            val listKeyword: MutableList<Keyword> = mutableListOf()
            keyword.forEach { k ->
                listKeyword.add(Keyword(k))
            }
            discuss.keyword = listKeyword

            // Comments
            val commentItem: MutableList<Comments> = mutableListOf()
            item.comments?.forEach { c ->
                commentItem.add(Comments(c?.namekomen.toString(), c?.photoProfileUrlkomen.toString(), c?.comment.toString()))
            }
            discuss.comments = commentItem

            result.add(discuss)
        }
        return result
    }

}
