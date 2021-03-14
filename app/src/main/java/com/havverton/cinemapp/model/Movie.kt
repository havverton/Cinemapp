package com.havverton.cinemapp.model
import kotlinx.serialization.*
import kotlinx.serialization.SerialName


class Movie (


    val pgAge: Int,

    val genres: String,

    val id: Long,
    val title:String,
    val imageUrl: String,
    val overview: String,
    val voteCount: Long,
    val cast: List<Actor>,

){
    fun getMovieId():Long{
        return this.id
    }
}


