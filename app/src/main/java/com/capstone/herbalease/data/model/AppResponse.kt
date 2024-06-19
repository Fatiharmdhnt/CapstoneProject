package com.capstone.herbalease.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

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

	//fix api call
	@JsonAdapter(RekomendasiMenuAdapter::class)
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

	//fix ui
	val listKeywords: List<Keyword>
		get() = keyword?.takeIf { it.isNotEmpty() }
			?.split(", ")
			?.map { Keyword(it) }
			?: emptyList()
	val listKandunganKeywords: List<Keyword>
		get() = kandungan?.takeIf { it.isNotEmpty() }
			?.split(", ")
			?.map { Keyword(it) }
			?: emptyList()
}

//fix api call
class RekomendasiMenuAdapter : JsonDeserializer<List<RekomendasiMenu>> {
	override fun deserialize(
		json: JsonElement?,
		typeOfT: Type?,
		context: JsonDeserializationContext?
	): List<RekomendasiMenu> {
		val rekomendasiMenuJson = json?.asString ?: return emptyList()
		val type = object : TypeToken<List<RekomendasiMenu>>() {}.type
		return Gson().fromJson(rekomendasiMenuJson, type)
	}
}

@Parcelize
data class RekomendasiMenu(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null
) : Parcelable
