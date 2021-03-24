package com.havverton.cinemapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.havverton.cinemapp.model.Movie


@Dao
interface FavoritesListDao {
    @Query("SELECT * FROM films_list")
    fun getAll(): List<Movie>
    @Query("SELECT * FROM films_list WHERE title == :title")
    fun getMovie(title:String): Movie
    @Insert
    fun insertAll(movies: List<Movie>)
    @Insert
    fun insert(movie: Movie):Long

    @Update
    fun updateAll(movies: List<Movie>)

    @Query("SELECT * FROM ")
    fun getAllFavorites(): List<Movie>
}
