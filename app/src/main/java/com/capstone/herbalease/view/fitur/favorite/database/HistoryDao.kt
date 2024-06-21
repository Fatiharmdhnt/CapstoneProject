package com.capstone.herbalease.view.fitur.favorite.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.capstone.herbalease.data.pref.Ingredient

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHistory(ingredient: Ingredient)

    @Query("SELECT * FROM history")
    suspend fun getHistory() : List<Ingredient>

    @Query("SELECT COUNT(*) FROM history WHERE history.id = :id")
    suspend fun checkHistory(id : Int) : Int

    @Query("DELETE FROM history WHERE history.id = :id")
    suspend fun removeHistory(id : Int) : Int
}