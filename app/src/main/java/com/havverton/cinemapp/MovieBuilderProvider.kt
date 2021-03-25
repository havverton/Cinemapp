package com.havverton.cinemapp

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.havverton.cinemapp.fragments.RetrofitModule
import com.havverton.cinemapp.model.*
import kotlinx.coroutines.*

object MovieBuilderProvider {
    suspend fun getMovieList(): List<Movie> {
        var moviesList: MutableList<Movie> = mutableListOf()
        var movies: List<JsonMovie> = emptyList()
        var genres: List<Genre> = emptyList()
        var url:String = ""
        try {
            movies = RetrofitModule.movieApi.getPopularMovieList().results
            genres = RetrofitModule.movieApi.getGenreList().genres
            url = RetrofitModule.movieApi.getApi().images.secureBaseURL
            movies.forEach {
                val goodMovie = Movie(
                    id = it.id,
                    title = it.title,
                    pgAge = if (it.adult) {
                        18
                    } else {
                        13
                    },
                    genres = fillGenres(it, genres),
                    imageUrl = "$url" + "w300" + "${it.posterPath}",
                    overview = it.overview,
                    voteCount = it.voteCount,
                    isFavorite = false
                )
                moviesList.add(goodMovie)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }

        return moviesList
    }


    private fun fillGenres(movie: JsonMovie, genres: List<Genre>): String {
        var genreString = ""

        movie.genreIDS.forEach { movieGenreId ->
            genres.forEach { genre ->
                if (movieGenreId == genre.id) {
                    genreString += "${genre.name},"
                }
            }
        }
        return genreString
    }


}