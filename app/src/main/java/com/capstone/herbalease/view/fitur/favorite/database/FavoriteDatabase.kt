package com.capstone.herbalease.view.fitur.favorite.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.capstone.herbalease.data.pref.Ingredients

@Database(
    entities = [Ingredients::class],
    version = 1, // Start from version 1 if you want to reset completely
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun FavoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDatabase::class.java,
                    "favorite_database"
                )
                    .fallbackToDestructiveMigration() // Ensure schema changes are applied
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
