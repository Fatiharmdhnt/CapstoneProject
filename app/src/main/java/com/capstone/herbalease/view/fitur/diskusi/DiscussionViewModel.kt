package com.capstone.herbalease.view.fitur.diskusi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.capstone.herbalease.data.model.Comments
import com.capstone.herbalease.data.model.ForumDiscussion
import com.capstone.herbalease.data.model.Keyword
import com.capstone.herbalease.data.model.response.discussion.GetDiscussionResponse
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
    init {
        // Load the initial session from the repository
        viewModelScope.launch {
            repository.getSession().collect { session ->
                _userSession.postValue(session)
            }
        }
    }
    fun setDiscussion() {
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

    fun dicussionResponseToForumDiscussion(response : List<GetDiscussionResponse>): MutableList<ForumDiscussion>? {
        var result : MutableList<ForumDiscussion>? = null
        response.forEach {
            val discuss : ForumDiscussion? = null
            discuss?.name = it.name.toString()
            discuss?.title = it.title.toString()
            discuss?.photoProfileUrl = it.photoProfileUrl.toString()
            discuss?.description = it.description.toString()
            discuss?.photoDiscussionUrl = it.photoDiscussionUrl.toString()

            //Keyword
            val keyword = it.keyword.toString().split(", ")
            val listKeyword : MutableList<Keyword>? = null
            keyword.forEach {
                listKeyword?.add(Keyword(it))
            }
            if (listKeyword != null) {
                discuss?.keyword = listKeyword
            }
            val commentItem : MutableList<Comments>? = null
            it.comments?.forEach {it ->
                commentItem?.add(Comments(it?.namekomen.toString(), it?.photoProfileUrlkomen.toString(), it?.comment.toString()))
            }
            discuss?.comments = commentItem
        }
        return result
    }
}
