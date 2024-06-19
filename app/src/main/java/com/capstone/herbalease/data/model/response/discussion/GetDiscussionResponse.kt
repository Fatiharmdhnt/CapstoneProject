package com.capstone.herbalease.data.model.response.discussion

import com.google.gson.annotations.SerializedName

data class GetDiscussionResponse(

	@field:SerializedName("GetDiscussionResponse")
	val getDiscussionResponse: List<GetDiscussionResponseItem?>? = null
)

data class GetDiscussionResponseItem(

	@field:SerializedName("photoProfileUrl")
	val photoProfileUrl: String? = null,

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
	val keyword: String? = null
)

data class CommentsItem(

	@field:SerializedName("namekomen")
	val namekomen: String? = null,

	@field:SerializedName("comment")
	val comment: String? = null,

	@field:SerializedName("photoProfileUrlkomen")
	val photoProfileUrlkomen: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
