package com.capstone.herbalease.data.model.retrofit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForumDiscussion(
    val name: String,
    val photoProfileUrl: String,
    val title: String,
    val description: String,
    val keyword: List<Keyword>,
    val photoDiscussionUrl: String?,
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
