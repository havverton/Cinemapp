package com.havverton.cinemapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.RoomDatabase
import androidx.room.RoomOpenHelper
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.havverton.cinemapp.adapters.MovieListAdapter
import com.havverton.cinemapp.db.AppDatabase
import com.havverton.cinemapp.fragments.FavoritesFragment
import com.havverton.cinemapp.fragments.FragmentMovieDetails
//import com.havverton.cinemapp.fragments.FragmentMovieDetails
import com.havverton.cinemapp.fragments.FragmentMoviesList
import com.havverton.cinemapp.model.Movie
import com.havverton.cinemapp.work.RefreshCacheWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), MovieListAdapter.ItemSelectedListener, FragmentMoviesList.FavoritesListener {
    var viewModel: DetailsViewModel? = null
    var movieslistFragment: FragmentMoviesList? = null
    var favoritesFragment: FavoritesFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            movieslistFragment = FragmentMoviesList()
            supportFragmentManager.beginTransaction().apply {
                add(R.id.main_frame, movieslistFragment!!, "list_fragment")
                commit()
            }
        } else {
            movieslistFragment =
                supportFragmentManager.findFragmentByTag("list_fragment") as? FragmentMoviesList
         //   favoritesFragment = supportFragmentManager.findFragmentByTag("favorites_fragment") as? FavoritesFragment
        }
        viewModel =
            ViewModelProvider(this, DetailsViewModelFactory()).get(DetailsViewModel::class.java)

        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.UNMETERED).build()

        WorkManager.getInstance(applicationContext).enqueue(
            PeriodicWorkRequest.Builder(RefreshCacheWork::class.java, 8, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build())
    }

    override fun openMovieDetails(item: Movie) {
        viewModel?.selectMovie(item)
       val moviesDetailFragment = FragmentMovieDetails()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_frame, moviesDetailFragment, "detail_fragment")
            addToBackStack(null)
            commit()
        }
    }
    override fun openFavoritesMovies() {
        val favoriteFragment = FavoritesFragment()

        supportFragmentManager.beginTransaction().apply {
            add(R.id.main_frame, favoriteFragment, "favorites_fragment")
            addToBackStack(null)
            commit()
        }
    }
    override fun addToFavorites(item:Movie){
        val db = AppDatabase.create(applicationContext)
        item.isFavorite = true
        CoroutineScope(Dispatchers.IO).launch {
            db.movieDao.update(item)
        }
        db.close()
        Toast.makeText(applicationContext,"Добавлено в избранное", Toast.LENGTH_SHORT).show()
    }


}