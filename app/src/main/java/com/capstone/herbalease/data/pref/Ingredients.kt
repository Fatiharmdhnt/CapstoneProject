package com.capstone.herbalease.data.pref

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Ingredients(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val description: String,
    val listKhasiat: List<String>,
    val listKeywords: List<String>,
    val listKandungan: List<String> = listOf(),
    val listKeluhan: List<String> = listOf(),
) : Parcelable

@Entity(tableName = "favorite")
data class Ingredien(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val description: String,
    val listKhasiat: List<String>,
    val listKeywords: List<String>,
    val listKandungan: List<String> = listOf(),
    val listKeluhan: List<String> = listOf(),
) : Serializable
@Entity(tableName = "history")
data class Ingredient(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val description: String,
    val listKhasiat: List<String>,
    val listKeywords: List<String>,
    val listKandungan: List<String> = listOf(),
    val listKeluhan: List<String> = listOf(),
) : Serializable