package com.havverton.cinemapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.havverton.cinemapp.model.Movie

@Database(entities = [Movie::class],version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract val movieDao: MovieListDao
    companion object{
        fun create(applicationContext:Context):AppDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            MovieListContract.CACHE_DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}