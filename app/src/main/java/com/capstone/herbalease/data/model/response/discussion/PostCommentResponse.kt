package com.capstone.herbalease.data.model.response.discussion

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class PostCommentResponse(

	@field:SerializedName("comments")
	val comments: List<CommentItem?>? = null,

	@field:SerializedName("comment")
	val comment: Comment? = null
) : Parcelable

@Parcelize
data class User(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable

@Parcelize
data class CommentItem(

	@field:SerializedName("User")
	val user: User? = null,

	@field:SerializedName("comment")
	val comment: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable

@Parcelize
data class Comment(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("comment")
	val comment: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("forumDiscussionId")
	val forumDiscussionId: String? = null
) : Parcelable
