package com.capstone.herbalease.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForumDiscussion(
    val name : String,
    val photoProfileUrl : String,
    val title : String,
    val description : String,
    val keyword : List<Keywords>,
    val photoDiscussionUrl : String,
    val comments : List<Comments>
) : Parcelable

@Parcelize
data class Keywords(
    val keyword : String
) : Parcelable

@Parcelize
data class Comments(
    val name : String,
    val photoProfileUrl : String,
    val comments : String
) : Parcelable
