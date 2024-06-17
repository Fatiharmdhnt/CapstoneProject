package com.capstone.herbalease.view.fitur.favorite.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

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
                        HistoryDatabase::class.java, "note_database")
                        .build()
                }
            }
            return INSTANCE as HistoryDatabase
        }
    }
}