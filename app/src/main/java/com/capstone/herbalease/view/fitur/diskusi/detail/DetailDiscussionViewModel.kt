package com.capstone.herbalease.view.fitur.diskusi.detail

import androidx.lifecycle.ViewModel
import com.capstone.herbalease.data.model.retrofit.Comments
import com.capstone.herbalease.data.model.retrofit.ForumDiscussion
import com.capstone.herbalease.di.FakeData

class DetailDiscussionViewModel : ViewModel(){

    private var listDiscussion : MutableList<ForumDiscussion> = FakeData.discussionList.toMutableList()

    fun sendComment(comment : String, title : String){
        listDiscussion.forEach{
            if (it.title == title){
                val komen = Comments(
                    "Siapa aja terserah",
                    "https://2.bp.blogspot.com/-ft63mqaDj-A/Ua9wD0Ii_BI/AAAAAAAAAKA/NWJBvBQngms/s1600/orang+utan+(3).jpg",
                    comment
                )
                it.comments?.add(komen)
            }
        }
        FakeData.discussionList = listDiscussion
    }
}