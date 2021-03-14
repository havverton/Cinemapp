package com.havverton.cinemapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.havverton.cinemapp.adapters.MovieListAdapter
import com.havverton.cinemapp.fragments.FragmentMovieDetails
//import com.havverton.cinemapp.fragments.FragmentMovieDetails
import com.havverton.cinemapp.fragments.FragmentMoviesList
import com.havverton.cinemapp.model.Movie

class MainActivity : AppCompatActivity(), MovieListAdapter.ItemSelectedListener {
    var viewModel: DetailsViewModel? = null
    var movieslistFragment: FragmentMoviesList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            movieslistFragment = FragmentMoviesList()
            supportFragmentManager.beginTransaction().apply {
                add(R.id.main_frame, movieslistFragment!!, "OLOLO")
                commit()
            }
        } else {
            movieslistFragment =
                supportFragmentManager.findFragmentByTag("OLOLO") as? FragmentMoviesList
        }
        viewModel =
            ViewModelProvider(this, DetailsViewModelFactory()).get(DetailsViewModel::class.java)
    }

    override fun openMovieDetails(item: Movie) {
        viewModel?.selectMovie(item)
       val moviesDetailFragment = FragmentMovieDetails()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_frame, moviesDetailFragment, "OLOLO1")
            addToBackStack(null)
            commit()
        }
    }

}