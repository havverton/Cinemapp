package com.havverton.cinemapp.model
import kotlinx.serialization.*


@Serializable
data class MoviePage (
    val page: Long,
    val results: List<JsonMovie>,

    @SerialName("total_pages")
    val totalPages: Long,

    @SerialName("total_results")
    val totalResults: Long
)
