package com.havverton.cinemapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.havverton.cinemapp.R
import com.havverton.cinemapp.model.Genre
import com.havverton.cinemapp.model.Movie

class MovieListAdapter:RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {
    var filmList : List<Movie> = emptyList()
    private var listener: ItemSelectedListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie,parent,false)
        val vh = MovieListViewHolder(view)
        val context = view.context
        if(context is ItemSelectedListener){
            listener = context
        }
        return vh
    }

    fun setMovieList(list: List<Movie>){
        filmList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return filmList.size
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val currentMovie = filmList[position]
        fillFields(holder,currentMovie)

    }

    private fun fillFields(holder: MovieListViewHolder, movie:Movie){
        holder.filmName.text = movie.title
        holder.reviews.text = "${ movie.voteCount} Reviews"
        holder.ageRating.text = "13"
        holder.genre.text = movie.genres
       // holder.duration.text = "${movie.runningTime} MINS"

        Glide
            .with(holder.itemView)
            .load(movie.imageUrl)
            .into(holder.filmPoster)

        holder.itemView.setOnClickListener{
            listener?.openMovieDetails(movie)
        }
    }


    class MovieListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val filmName:TextView = itemView.findViewById(R.id.filmName)
        val reviews:TextView = itemView.findViewById(R.id.filmReviews)
        val genre:TextView = itemView.findViewById(R.id.filmGenre)
        val duration:TextView = itemView.findViewById(R.id.filmDuration)
        val filmPoster: ImageView = itemView.findViewById(R.id.filmPoster)
        val ageRating: TextView = itemView.findViewById(R.id.pgAge)
        }


    interface ItemSelectedListener{
        fun openMovieDetails(item:Movie)
    }


}

