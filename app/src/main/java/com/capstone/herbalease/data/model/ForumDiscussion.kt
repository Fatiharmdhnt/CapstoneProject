package com.capstone.herbalease.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForumDiscussion(
    var name: String,
    var photoProfileUrl: String,
    var title: String,
    var description: String,
    var keyword: List<Keyword>,
    var photoDiscussionUrl: String?,
    var comments : MutableList<Comments>?
) : Parcelable

@Parcelize
data class Keyword(
    val keyword : String
) : Parcelable

@Parcelize
data class Comments(
    val name : String,
    val photoProfileUrl : String,
    val comments : String
) : Parcelable
