package com.capstone.herbalease.data.pref

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.capstone.herbalease.data.model.response.RekomendasiMenu
import java.io.Serializable

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
