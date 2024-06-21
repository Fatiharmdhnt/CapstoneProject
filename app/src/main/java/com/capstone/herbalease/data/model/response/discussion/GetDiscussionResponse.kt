package com.capstone.herbalease.data.model.response.discussion

import com.google.gson.annotations.SerializedName

data class GetDiscussionResponse(

	@field:SerializedName("GetDiscussionResponse")
	val getDiscussionResponse: List<GetDiscussionResponseItem?>? = null
)

data class UserItem(

	@field:SerializedName("profile_picture_url")
	val profilePictureUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
)

data class GetDiscussionResponseItem(

	@field:SerializedName("comments")
	val comments: List<CommentsItem?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("photoDiscussionUrl")
	val photoDiscussionUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("keyword")
	val keyword: String? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("user")
	val user: UserItem? = null
)

data class CommentsItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("comment")
	val comment: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("user")
	val user: UserItem? = null,

	@field:SerializedName("forumDiscussionId")
	val forumDiscussionId: Int? = null
)
