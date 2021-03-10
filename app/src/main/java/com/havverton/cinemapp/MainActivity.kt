package com.havverton.cinemapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.havverton.cinemapp.adapters.MovieListAdapter
import com.havverton.cinemapp.fragments.FragmentMovieDetails
import com.havverton.cinemapp.fragments.FragmentMoviesList

class MainActivity : AppCompatActivity(), MovieListAdapter.ClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var movieslistFragment: FragmentMoviesList? = null

        if (savedInstanceState == null) {
            movieslistFragment =
                FragmentMoviesList()

            supportFragmentManager.beginTransaction().apply {
                add(R.id.main_frame, movieslistFragment!!, "OLOLO")
                commit()
            }
        } else {
            movieslistFragment =
                supportFragmentManager.findFragmentByTag("OLOLO") as? FragmentMoviesList
        }
    }

    override fun openMovieDetails() {
        val moviesDetailFragment =
            FragmentMovieDetails()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_frame, moviesDetailFragment, "OLOLO1")
            addToBackStack(null)
            commit()
        }
    }
}