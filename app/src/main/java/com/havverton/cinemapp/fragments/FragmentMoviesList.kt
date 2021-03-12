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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.academy.fundamentals.homework.model.Movie
import com.havverton.cinemapp.DetailsViewModel
import com.havverton.cinemapp.DetailsViewModelFactory
import com.havverton.cinemapp.Film
import com.havverton.cinemapp.R
import com.havverton.cinemapp.adapters.MovieListAdapter
import com.havverton.cinemapp.data.JsonMovieRepository
import com.havverton.cinemapp.data.MovieRepository
import kotlinx.coroutines.*

class FragmentMoviesList : Fragment() {
    var clickListener: MovieListAdapter.ItemSelectedListener? = null
    private var movieBlock: ImageView? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: MovieListAdapter? = null
    var viewModel: DetailsViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       viewModel  = ViewModelProvider(activity!!.viewModelStore, DetailsViewModelFactory()).get(DetailsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val scope = CoroutineScope(Dispatchers.IO + Job())
        scope.launch {
            viewModel?.fillMovieList(JsonMovieRepository(view.context).loadMovies())
        }
        recyclerView = view.findViewById(R.id.rv_list)
        recyclerView?.layoutManager = GridLayoutManager(context, 2)
        viewModel?.movieList!!.observe(this,{
            adapter = MovieListAdapter()
            adapter?.setList(viewModel?.movieList!!.value!!)
            recyclerView?.adapter = adapter
        })
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