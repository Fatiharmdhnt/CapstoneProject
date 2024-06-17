package com.capstone.herbalease.view.fitur.favorite.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.capstone.herbalease.data.pref.Ingredients

@Database(
    entities = [Ingredients::class],
    version = 1
)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun FavoriteDao() : FavoriteDao

    companion object{
        @Volatile
        private var INSTANCE: FavoriteDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): FavoriteDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteDatabase::class.java, "note_database")
                        .build()
                }
            }
            return INSTANCE as FavoriteDatabase
        }
    }
}