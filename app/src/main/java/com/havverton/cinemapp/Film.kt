package com.havverton.cinemapp

import android.graphics.drawable.Drawable
import android.media.Image

data class Film(
    val name:String,
    val genre:String,
    val reviews:String,
    val duration: String,
    val poster: Drawable
) {
}