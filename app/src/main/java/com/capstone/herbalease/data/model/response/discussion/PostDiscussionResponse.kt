package com.capstone.herbalease.data.model.response.discussion

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class PostDiscussionResponse(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("photoDiscussionUrl")
	val photoDiscussionUrl: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("keyword")
	val keyword: String? = null,

	@field:SerializedName("userId")
	val userId: Int? = null
)
