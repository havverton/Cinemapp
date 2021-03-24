package com.havverton.cinemapp


import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
    @Serializable
    data class ImagesResponse (
        val images: Images,

        @SerialName("change_keys")
        val changeKeys: List<String>
    )

    @Serializable
    data class Images (
        @SerialName("base_url")
        val baseURL: String,

        @SerialName("secure_base_url")
        val secureBaseURL: String,

        @SerialName("backdrop_sizes")
        val backdropSizes: List<String>,

        @SerialName("logo_sizes")
        val logoSizes: List<String>,

        @SerialName("poster_sizes")
        val posterSizes: List<String>,

        @SerialName("profile_sizes")
        val profileSizes: List<String>,

        @SerialName("still_sizes")
        val stillSizes: List<String>
    )

