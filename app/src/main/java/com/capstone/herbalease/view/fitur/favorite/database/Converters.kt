package com.capstone.herbalease.view.fitur.favorite.database

import androidx.room.TypeConverter
import com.capstone.herbalease.data.model.retrofit.RekomendasiMenu
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converters {
    @TypeConverter
    @JvmStatic
    fun fromList(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    @JvmStatic
    fun toList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    @JvmStatic
    fun fromRekomendasiMenuList(list: List<RekomendasiMenu>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    @JvmStatic
    fun toRekomendasiMenuList(value: String): List<RekomendasiMenu> {
        val listType = object : TypeToken<List<RekomendasiMenu>>() {}.type
        return Gson().fromJson(value, listType)
    }
}