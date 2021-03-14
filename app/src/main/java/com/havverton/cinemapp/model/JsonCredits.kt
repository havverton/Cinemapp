package com.havverton.cinemapp.model
import kotlinx.serialization.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.*

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class JSONCredits (
    val id: Long,
    val cast: List<Cast>,
    val crew: List<Cast>
)

@Serializable
data class Cast (
    val adult: Boolean,
    val gender: Long,
    val id: Long,

    val name: String,

    @SerialName("original_name")
    val originalName: String,

    val popularity: Double,

    @SerialName("profile_path")
    val profilePath: String? = null,

    @SerialName("cast_id")
    val castID: Long? = null,

    val character: String? = null,

    @SerialName("credit_id")
    val creditID: String,

    val order: Long? = null,
    val department: String? = null,
    val job: String? = null
)
