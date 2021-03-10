package com.havverton.cinemapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.academy.fundamentals.homework.model.Movie
import com.havverton.cinemapp.Film
import com.havverton.cinemapp.R
import com.havverton.cinemapp.adapters.MovieListAdapter
import com.havverton.cinemapp.data.JsonMovieRepository
import com.havverton.cinemapp.data.MovieRepository
import kotlinx.coroutines.*

class FragmentMoviesList : Fragment() {
    var clickListener: MovieListAdapter.ClickListener? = null
    private var movieBlock: ImageView? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: MovieListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var filmList: List<Movie> = emptyList()
        val scope = CoroutineScope(Dispatchers.IO + Job())
        val test = scope.async {
            JsonMovieRepository(view.context).loadMovies()
        }


        recyclerView = view.findViewById<RecyclerView>(R.id.rv_list)
        recyclerView?.layoutManager = GridLayoutManager(context, 2)
        scope.launch {
            adapter = MovieListAdapter(test.await())
            val scope1 = CoroutineScope(Dispatchers.Main + Job())
            scope1.launch {
                recyclerView?.adapter = adapter
            }

        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MovieListAdapter.ClickListener) {
            clickListener = context
        }

    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
    }

    interface UpdateFragment {
        fun updateFragment()
    }

}