package com.havverton.cinemapp

import android.content.Context
import android.content.res.Resources
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

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

        val posterBW = AppCompatResources.getDrawable(view.context,R.drawable.blackwidow)

        val filmList:List<Film> = listOf(
            Film("BlackWidow","Action","125 review","137 min", posterBW!!),
            Film("Avengers","Action","125 review","137 min", posterBW),
            Film("Avengers2","Action","125 review","137 min", posterBW),
            Film("Avengers3","Action","125 review","137 min", posterBW),
            Film("BlackWidow","Action","125 review","137 min", posterBW),
            Film("Avengers4","Action","125 review","137 min", posterBW),
            Film("Avengers5","Action","125 review","137 min", posterBW),
            Film("Avengers6","Action","125 review","137 min", posterBW)
        )
        recyclerView = view.findViewById<RecyclerView>(R.id.rv_list)
        recyclerView?.layoutManager = GridLayoutManager(context,2)
        adapter = MovieListAdapter(filmList)

        recyclerView?.adapter = adapter
        super.onViewCreated(view, savedInstanceState)



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


}