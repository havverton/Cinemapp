package com.havverton.cinemapp

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.havverton.cinemapp.fragments.RetrofitModule
import com.havverton.cinemapp.model.*
import kotlinx.coroutines.*
import okhttp3.Dispatcher
import retrofit2.http.GET

object MovieBuilder {
    var viewModel: DetailsViewModel? = null

    suspend fun getMovieList(context: ViewModelStore) : List<Movie> {
        var moviesList: MutableList<Movie> = mutableListOf()
        val scope = CoroutineScope(Dispatchers.IO + Job())
        val scope2 = CoroutineScope(Dispatchers.IO + Job())
        val scope3 = CoroutineScope(Dispatchers.IO + Job())
        var movies: List<JsonMovie> = emptyList()
        var genres: List<Genre> = emptyList()
        var baseURL: String = ""
        var list: List<Movie> = emptyList()
        viewModel =
            ViewModelProvider(context, DetailsViewModelFactory()).get(DetailsViewModel::class.java)
       val job3 =  scope.async {
            val job1:Deferred<List<JsonMovie>> = scope2.async {
                movies = RetrofitModule.movieApi.getPopularMovieList().results
                movies
            }
            val job2:Deferred< List<Genre>> = scope2.async {
                genres = RetrofitModule.movieApi.getGenreList().genres
                genres
            }
            val job3 : Deferred<String> = scope2.async {
              val url = RetrofitModule.movieApi.getApi().images.secureBaseURL
                url
            }




            val job = scope2.async {
                job1.await()
                movies.forEach {
                    val goodMovie = Movie(
                        id = it.id,
                        title = it.title,
                        pgAge = if(it.adult){18}else{13},
                        genres = fillGenres(it, job2.await()),
                        imageUrl = "${job3.await()}" + "w300" + "${it.posterPath}",
                        overview = it.overview,
                        voteCount = it.voteCount,
                        cast = emptyList()
                    )
                    moviesList.add(goodMovie)
                }
                moviesList
            }
            job.await()


        }
        return job3.await()
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