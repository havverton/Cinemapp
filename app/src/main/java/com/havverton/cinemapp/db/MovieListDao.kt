package com.havverton.cinemapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.havverton.cinemapp.model.Movie


@Dao
interface MovieListDao {
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
    @Update
    fun update(movie: Movie)

    @Query("SELECT * FROM films_list WHERE isFavorite = :isFavorite ")
    fun getAllFavorites(isFavorite: Boolean): List<Movie>
}
