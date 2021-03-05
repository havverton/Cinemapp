package com.havverton.cinemapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class FragmentMoviesList : Fragment() {
    var clickListener: ClickListener? = null
    private var movieBlock: ImageView? = null
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
        super.onViewCreated(view, savedInstanceState)
        movieBlock = view.findViewById<ImageView>(R.id.moviesListBlock).apply {
            setOnClickListener { clickListener?.openMovieDetails() }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ClickListener) {
            clickListener = context
        }

    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
    }

    fun openMovie() {

    }


    interface ClickListener {
        fun openMovieDetails()
    }

}