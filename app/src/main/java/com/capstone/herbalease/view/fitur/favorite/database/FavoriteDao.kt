package com.capstone.herbalease.view.fitur.favorite.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.capstone.herbalease.data.pref.Ingredients

@Dao
interface FavoriteDao {

    @Insert
    fun addFavorite(ingredients: Ingredients)

    @Query("SELECT * FROM favorite")
    fun getFavorite() : List<Ingredients>

    @Query("SELECT COUNT(*) FROM favorite WHERE favorite.id = :id")
    fun checkFavorite(id : Int) : Int

    @Query("DELETE FROM favorite WHERE favorite.id = :id")
    fun removeFavorite(id : Int) : Int
}