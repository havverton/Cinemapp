package com.havverton.cinemapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.academy.fundamentals.homework.model.Actor
import com.android.academy.fundamentals.homework.model.Genre
import com.android.academy.fundamentals.homework.model.Movie
import com.bumptech.glide.Glide
import com.havverton.cinemapp.R
import com.havverton.cinemapp.adapters.ActorsAdapter
import org.w3c.dom.Text


class FragmentMovieDetails(movie : Movie) : Fragment() {
    val movie = movie
    var recyclerView:RecyclerView? = null
    var adapter: ActorsAdapter? = null
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
        val bg = view.findViewById<ImageView>(R.id.detailsBG)
        Glide.with(view.context)
            .load(movie.detailImageUrl)
            .into(bg)
        val title = view.findViewById<TextView>(R.id.detailsTitle)
        title.text = movie.title
        val lore = view.findViewById<TextView>(R.id.detailsLore)
        lore.text = movie.storyLine
        var genre:TextView =view.findViewById(R.id.detailsGenres)
        genre.text = fillGenres(movie.genres)
        val reviews = view.findViewById<TextView>(R.id.detailsReviews)
        reviews.text = "${movie.reviewCount} Reviews"
        val pgAge = view.findViewById<TextView>(R.id.detailsPgAge)
        pgAge.text = "${movie.pgAge}+"

        val actors:List<Actor> = movie.actors
        recyclerView = view.findViewById(R.id.rv_actors)
        if(actors.isNotEmpty()){
            adapter = ActorsAdapter(actors)
        }
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
    }

    fun fillGenres(genres:List<Genre>):String{
        var genreString = ""
        val iterator = genres.iterator()
        do {
            genreString +=iterator.next().name
            if(iterator.hasNext()) {
                genreString += ", "
            }
        } while(iterator.hasNext())
        return genreString
    }
}