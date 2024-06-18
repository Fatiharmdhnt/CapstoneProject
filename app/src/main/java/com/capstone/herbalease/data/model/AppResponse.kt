package com.capstone.herbalease.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AppResponse(

	@field:SerializedName("AppResponse")
	val appResponse: List<AppResponseItem?>? = null
) : Parcelable

@Parcelize
data class AppResponseItem(

	@field:SerializedName("khasiat")
	val khasiat: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("rekomendasi_menu")
	val rekomendasiMenu: List<RekomendasiMenu?>? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("keyword")
	val keyword: String? = null,

	@field:SerializedName("kandungan")
	val kandungan: String? = null
) : Parcelable {

	val listKandungan: List<String>
		get() = kandungan?.split(", ") ?: emptyList()

	val listKhasiat: List<String>
		get() = khasiat?.split(", ") ?: emptyList()

	val listKeluhan: List<String>
		get() = keyword?.split(", ") ?: emptyList()
}

@Parcelize
data class RekomendasiMenu(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null
) : Parcelable
