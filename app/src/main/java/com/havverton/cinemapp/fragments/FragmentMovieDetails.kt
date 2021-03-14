
package com.havverton.cinemapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.havverton.cinemapp.DetailsViewModel
import com.havverton.cinemapp.DetailsViewModelFactory
import com.havverton.cinemapp.R
import com.havverton.cinemapp.adapters.ActorsAdapter
import com.havverton.cinemapp.model.Actor
import com.havverton.cinemapp.model.Cast
import com.havverton.cinemapp.model.Genre
//import com.havverton.cinemapp.adapters.ActorsAdapter
import com.havverton.cinemapp.model.Movie
import kotlinx.coroutines.*


class FragmentMovieDetails : Fragment() {
    var viewModel: DetailsViewModel? = null
    var recyclerView:RecyclerView? = null
    var adapter: ActorsAdapter? = null
    var cast: List<Cast> = emptyList()
    var actors: List<Actor> = emptyList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(activity!!.viewModelStore,DetailsViewModelFactory()).get(DetailsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillDetails(viewModel!!.currentMovie.value!!, view)



        recyclerView = view.findViewById(R.id.rv_actors)
        adapter = ActorsAdapter()
        val scope2 = CoroutineScope(Dispatchers.IO + Job())
        val scope = CoroutineScope(Dispatchers.Main + Job())

        val job4: Deferred<List<Actor>> = scope2.async {
            var actors: MutableList<Actor> = mutableListOf()
            cast = RetrofitModule.movieApi.getCast(viewModel?.currentMovie?.value!!.id).cast

            cast.forEach {
                val actor = Actor(
                    name = it.name,
                    imageUrl = "https://image.tmdb.org/t/p/" + "w300" + "${it.profilePath}",
                    id = it.id.toInt()
                )
                actors.add(actor)
            }
            actors
        }

        scope2.launch {
            actors = job4.await()
            scope.launch { if (actors.isNotEmpty()) {

                adapter?.setActorsList(actors)
                recyclerView?.adapter = adapter
                recyclerView?.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                Toast.makeText(view.context,"Прошёл", Toast.LENGTH_SHORT).show()
            } else {
                view.findViewById<TextView>(R.id.castLine).visibility = View.GONE
                recyclerView?.visibility = View.GONE
            }
            } }
        }


    fun fillDetails(movie:Movie,view: View){
        val bg = view.findViewById<ImageView>(R.id.detailsBG)
        val title = view.findViewById<TextView>(R.id.detailsTitle)
        val lore = view.findViewById<TextView>(R.id.detailsLore)
        val genre:TextView = view.findViewById(R.id.detailsGenres)
        val reviews = view.findViewById<TextView>(R.id.detailsReviews)
        val pgAge = view.findViewById<TextView>(R.id.detailsPgAge)

        title.text = movie.title
        lore.text = movie.overview
        genre.text = movie.genres
        reviews.text = "${movie.voteCount} Reviews"
        pgAge.text = "${movie.pgAge}+"

        Glide.with(view.context)
            .load(movie.imageUrl)
            .into(bg)
    }

}
