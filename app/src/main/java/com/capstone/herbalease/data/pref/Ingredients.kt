package com.capstone.herbalease.data.pref

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

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