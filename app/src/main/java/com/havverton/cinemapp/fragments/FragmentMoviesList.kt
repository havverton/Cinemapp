package com.havverton.cinemapp.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.havverton.cinemapp.*
import com.havverton.cinemapp.R
import com.havverton.cinemapp.adapters.MovieListAdapter
import com.havverton.cinemapp.db.AppDatabase
import com.havverton.cinemapp.model.GenrePage
import com.havverton.cinemapp.model.JSONCredits
import com.havverton.cinemapp.model.Movie
import com.havverton.cinemapp.model.MovieResponse
import com.havverton.cinemapp.work.RefreshCacheWork
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

class FragmentMoviesList : Fragment() {
    var selectItemListener: MovieListAdapter.ItemSelectedListener? = null
    var favoritesListener: FavoritesListener? = null
    private var movieBlock: ImageView? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: MovieListAdapter? = null
    var viewModel: DetailsViewModel? = null
    var movies: List<Movie> = emptyList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(activity!!.viewModelStore, DetailsViewModelFactory()).get(
            DetailsViewModel::class.java
        )
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.rv_list)
        val orientation = resources.configuration.orientation
        if(orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView?.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL,false)
        }else{
            recyclerView?.layoutManager = GridLayoutManager(context, 1, RecyclerView.HORIZONTAL,false)
        }



        val scope = CoroutineScope(Dispatchers.IO)
        val movieJob = scope.async {
            val movies = MovieBuilderProvider.getMovieList()
            movies
        }

        scope.launch {

            adapter = MovieListAdapter()
            val dbMovies = readMoviesFromDB()
            viewModel?.fillMovieList(dbMovies)
            movies = movieJob.await()
            viewModel?.fillMovieList(movies)
            withContext(Dispatchers.Main) {
                viewModel?.movieList!!.observe(viewLifecycleOwner, {
                    if(it.isNotEmpty() ){
                        if(adapter == null){
                            adapter = MovieListAdapter()
                        }
                        adapter!!.setMovieList(it)
                        recyclerView?.adapter = adapter
                    }

                })
            }
        }

        val favoritesBtn = view.findViewById<TextView>(R.id.favorites_button)
        favoritesBtn.setOnClickListener{
         favoritesListener?.openFavoritesMovies()
        }

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MovieListAdapter.ItemSelectedListener) {
            selectItemListener = context
        }
        if (context is FavoritesListener) {
            favoritesListener = context
        }

    }

    override fun onDetach() {
        super.onDetach()
        selectItemListener = null
        favoritesListener = null
    }


    fun readMoviesFromDB():List<Movie>{
        val db = AppDatabase.create(requireContext().applicationContext)
        val movies =  db.movieDao.getAll()
        db.close()
        return movies
    }

    interface FavoritesListener{
        fun openFavoritesMovies()
    }
}

interface MovieApi {
    @GET("configuration?api_key=${AppConfig.API_KEY}")
    suspend fun getApi(): ImagesResponse

    @GET("movie/popular?api_key=${AppConfig.API_KEY}&language=en-EN")
    suspend fun getPopularMovieList(): MovieResponse

    @GET("genre/movie/list?api_key=${AppConfig.API_KEY}&language=en-EN")
    suspend fun getGenreList(): GenrePage

    @GET("movie/{id}/credits?api_key=6e466b67854a32b973cf8e3f9dc31068&language=en-US")
    suspend fun getCast(@Path("id") id: Long): JSONCredits

}

object RetrofitModule {
    private val json = Json { ignoreUnknownKeys = true }

    @Suppress("EXPERIMENTAL_API_USAGE")
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(AppConfig.BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
    val movieApi: MovieApi = retrofit.create()
}

