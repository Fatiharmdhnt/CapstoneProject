package com.capstone.herbalease.view.fitur.diskusi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.herbalease.data.model.ForumDiscussion
import com.capstone.herbalease.di.FakeData

class DiscussionViewModel : ViewModel(){

    private val _listDiscussion = MutableLiveData<List<ForumDiscussion>>()
    val listDiscussion : LiveData<List<ForumDiscussion>> get() = _listDiscussion

    fun setDiscussion(){
        _listDiscussion.postValue(FakeData.discussionList)
    }
}