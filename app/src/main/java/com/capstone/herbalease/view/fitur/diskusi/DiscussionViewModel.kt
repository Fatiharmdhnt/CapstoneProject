package com.capstone.herbalease.view.fitur.diskusi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.herbalease.data.model.response.ForumDiscussion
import com.capstone.herbalease.di.FakeData

class DiscussionViewModel : ViewModel() {

    private val _listDiscussion = MutableLiveData<List<ForumDiscussion>>()
    val listDiscussion: LiveData<List<ForumDiscussion>> get() = _listDiscussion

    fun setDiscussion() {
        _listDiscussion.postValue(FakeData.discussionList)
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
}
