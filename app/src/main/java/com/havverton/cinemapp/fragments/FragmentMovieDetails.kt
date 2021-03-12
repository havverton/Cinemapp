package com.havverton.cinemapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.academy.fundamentals.homework.model.Actor
import com.android.academy.fundamentals.homework.model.Genre
import com.android.academy.fundamentals.homework.model.Movie
import com.bumptech.glide.Glide
import com.havverton.cinemapp.DetailsViewModel
import com.havverton.cinemapp.DetailsViewModelFactory
import com.havverton.cinemapp.R
import com.havverton.cinemapp.adapters.ActorsAdapter


class FragmentMovieDetails : Fragment() {
    var viewModel: DetailsViewModel? = null
    var recyclerView:RecyclerView? = null
    var adapter: ActorsAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(activity!!.viewModelStore,DetailsViewModelFactory()).get(DetailsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
           fillDetails(viewModel!!.currentMovie.value!!,view)


        recyclerView = view.findViewById(R.id.rv_actors)
        adapter = ActorsAdapter()
        if(viewModel?.currentMovie?.value!!.actors.isNotEmpty()){
            adapter?.setActorsList(viewModel!!.currentMovie.value!!.actors)
            recyclerView?.adapter = adapter
            recyclerView?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        }else{
            view.findViewById<TextView>(R.id.castLine).visibility = View.GONE
            recyclerView?.visibility = View.GONE
        }
    }

    fun fillDetails(movie:Movie,view: View){
        val bg = view.findViewById<ImageView>(R.id.detailsBG)
        val title = view.findViewById<TextView>(R.id.detailsTitle)
        val lore = view.findViewById<TextView>(R.id.detailsLore)
        val genre:TextView = view.findViewById(R.id.detailsGenres)
        val reviews = view.findViewById<TextView>(R.id.detailsReviews)
        val pgAge = view.findViewById<TextView>(R.id.detailsPgAge)

        title.text = movie.title
        lore.text = movie.storyLine
        genre.text = fillGenres(movie.genres)
        reviews.text = "${movie.reviewCount} Reviews"
        pgAge.text = "${movie.pgAge}+"

        Glide.with(view.context)
            .load(movie.detailImageUrl)
            .into(bg)
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

