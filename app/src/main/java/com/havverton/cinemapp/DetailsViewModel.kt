package com.havverton.cinemapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.havverton.cinemapp.model.Genre
import com.havverton.cinemapp.model.Movie

class DetailsViewModel : ViewModel() {
    private val _currentMovie = MutableLiveData<Movie>()
    private val _movieList = MutableLiveData<List<Movie>>(emptyList())
    private val _favoritesList = MutableLiveData<List<Movie>>(emptyList())


    val currentMovie : LiveData<Movie> get() = _currentMovie
    val movieList : LiveData<List<Movie>> get() = _movieList
    val favoritesList : LiveData<List<Movie>> get() =  _favoritesList


    fun selectMovie(movie:Movie){
        _currentMovie.postValue(movie)
    }

    fun fillMovieList(list : List<Movie>){
        _movieList.postValue(list)
    }

    fun fillFavoritesList(list : List<Movie>){
        _favoritesList.postValue(list)
    }


}