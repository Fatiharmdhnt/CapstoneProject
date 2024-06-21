package com.capstone.herbalease.view.fitur.favorite.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.capstone.herbalease.data.pref.Ingredients

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(ingredients: Ingredients)

    @Query("SELECT * FROM favorite")
    suspend fun getFavorite() : List<Ingredients>

    @Query("SELECT COUNT(*) FROM favorite WHERE favorite.id = :id")
    suspend fun checkFavorite(id : Int) : Int

    @Query("DELETE FROM favorite WHERE favorite.id = :id")
    suspend fun removeFavorite(id : Int) : Int
}