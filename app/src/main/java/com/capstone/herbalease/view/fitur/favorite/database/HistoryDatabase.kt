package com.capstone.herbalease.view.fitur.favorite.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.capstone.herbalease.data.pref.Ingredient

@Database(
    entities = [Ingredient::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class HistoryDatabase : RoomDatabase(){

    abstract fun HistoryDao() : HistoryDao

    companion object{
        @Volatile
        private var INSTANCE: HistoryDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): HistoryDatabase {
            if (INSTANCE == null) {
                synchronized(HistoryDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        HistoryDatabase::class.java, "history_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as HistoryDatabase
        }
    }
}