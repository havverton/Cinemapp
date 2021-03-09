package com.havverton.cinemapp

import android.os.Bundle
import android.util.LayoutDirection
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class FragmentMovieDetails : Fragment() {
    var recyclerView:RecyclerView? = null
    var adapter:ActorsAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val actPic = AppCompatResources.getDrawable(view.context,R.drawable.stark)
        val actors:List<Actor> = listOf(
            Actor("Robert Downey Jr.", actPic!!),
            Actor("Robert Downey Jr.", actPic),
            Actor("Robert Downey Jr.", actPic),
            Actor("Robert Downey Jr.", actPic),
            Actor("Robert Downey Jr.", actPic)
        )

        recyclerView = view.findViewById(R.id.rv_actors)
        adapter = ActorsAdapter(actors)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
    }
}