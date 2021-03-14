package com.havverton.cinemapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.havverton.cinemapp.*
import com.havverton.cinemapp.adapters.MovieListAdapter
import com.havverton.cinemapp.model.GenrePage
import com.havverton.cinemapp.model.JSONCredits
import com.havverton.cinemapp.model.Movie
import com.havverton.cinemapp.model.MoviePage
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

class FragmentMoviesList : Fragment() {
    var clickListener: MovieListAdapter.ItemSelectedListener? = null
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
        recyclerView?.layoutManager = GridLayoutManager(context, 2)
        val scope = CoroutineScope(Dispatchers.IO)
        val movieJob = scope.async {
           val movies = MovieBuilder.getMovieList(viewModelStore)
            movies
        }

        scope.launch {
            movies = movieJob.await()
            adapter = MovieListAdapter()
            adapter!!.setMovieList(movies)

            withContext(Dispatchers.Main){
                viewModel?.movieList!!.observe(viewLifecycleOwner, {
                    recyclerView?.adapter = adapter
                })
            }
        }




    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MovieListAdapter.ItemSelectedListener) {
            clickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
    }
}

interface MovieApi {
    @GET("configuration?api_key=${AppConfig.API_KEY}")
    suspend fun getApi(): TMDBapi
    @GET("movie/popular?api_key=${AppConfig.API_KEY}&language=en-US")
    suspend fun getPopularMovieList() : MoviePage
    @GET("genre/movie/list?api_key=${AppConfig.API_KEY}&language=en-US")
    suspend fun getGenreList() :GenrePage
    @GET("movie/{id}/credits?api_key=6e466b67854a32b973cf8e3f9dc31068&language=en-US")
    suspend fun getCast(@Path("id") id: Long) :JSONCredits

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

