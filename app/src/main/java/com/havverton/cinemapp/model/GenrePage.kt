package com.havverton.cinemapp.model
import kotlinx.serialization.*


@Serializable
data class GenrePage (
    val genres: List<Genre>
)

@Serializable
data class Genre (
    val id: Long,
    val name: String
)

