package com.havverton.cinemapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.academy.fundamentals.homework.model.Genre
import com.android.academy.fundamentals.homework.model.Movie
import com.bumptech.glide.Glide
import com.havverton.cinemapp.R

class MovieListAdapter(filmList:List<Movie>):RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {
    val filmList = filmList
    private var listener: ClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie,parent,false)
        val vh =
            MovieListViewHolder(
                view
            )
        val context = view.context
        if(context is ClickListener){
            listener = context
        }


        return vh
    }

    override fun getItemCount(): Int {
        return filmList.size
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
       holder.filmName.text = filmList[position].title
        holder.reviews.text = "${ filmList[position].reviewCount} Reviews"
        holder.ageRating.text = "${filmList[position].pgAge}+"

        holder.genre.text = fillGenres(filmList[position].genres)
        holder.duration.text = "${filmList[position].runningTime} MINS"
        Glide
            .with(holder.itemView)
            .load(filmList[position].imageUrl)
            .into(holder.filmPoster)


        holder.itemView.setOnClickListener{
            listener?.openMovieDetails(filmList[position])
        }
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


    class MovieListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val filmName:TextView = itemView.findViewById(R.id.filmName)
        val reviews:TextView = itemView.findViewById(R.id.filmReviews)
        val genre:TextView = itemView.findViewById(R.id.filmGenre)
        val duration:TextView = itemView.findViewById(R.id.filmDuration)
        val filmPoster: ImageView = itemView.findViewById(R.id.filmPoster)
        val ageRating: TextView = itemView.findViewById(R.id.pgAge)
        }


    interface ClickListener{
        fun openMovieDetails(movie: Movie)
    }

}

