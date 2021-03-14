package com.havverton.cinemapp.model
import kotlinx.serialization.*
import kotlinx.serialization.SerialName


data class Actor (
    val name: String,
    val imageUrl:String,
    val id : Int
)

