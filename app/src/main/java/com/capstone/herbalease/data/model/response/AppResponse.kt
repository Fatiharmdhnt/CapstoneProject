package com.capstone.herbalease.data.model.response

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
    var khasiat: String? = null,

    @field:SerializedName("nama")
    var nama: String? = null,

    @field:SerializedName("rekomendasi_menu")
    var rekomendasiMenu: List<RekomendasiMenu?>? = null,

    @field:SerializedName("image_url")
    var imageUrl: String? = null,

    @field:SerializedName("id")
    var id: Int? = null,

    @field:SerializedName("deskripsi")
    var deskripsi: String? = null,

    @field:SerializedName("keyword")
    var keyword: String? = null,

    @field:SerializedName("kandungan")
    var kandungan: String? = null
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