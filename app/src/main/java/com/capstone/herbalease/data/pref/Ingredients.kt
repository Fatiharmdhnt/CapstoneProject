package com.capstone.herbalease.data.pref

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.capstone.herbalease.data.model.retrofit.RekomendasiMenu
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
@Entity(tableName = "favorite")
data class Ingredients(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String,
    val description: String,
    val listKhasiat: List<String>,
    val listKeywords: List<String>,
    val listKandungan: List<String>,
    val listRekomendasi: List<RekomendasiMenu>
) : Parcelable

@Entity(tableName = "history")
data class Ingredient(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String,
    val description: String,
    val listKhasiat: List<String>,
    val listKeywords: List<String>,
    val listKandungan: List<String>,
    val listRekomendasi: List<RekomendasiMenu>
) : Serializable
