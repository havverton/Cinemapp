package com.havverton.cinemapp.db

import android.provider.BaseColumns

object MovieListContract {
    const val CACHE_DB_NAME = "Films.db"
    const val FAVORITES_DB_NAME = "Favorites.db"

    object Movies{
        const val TABLE_NAME = "films_list"
        const val FAVORITES_TABLE_NAME = "favorites_list"
        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_GENRES = "genres"
        const val COLUMN_NAME_AGE = "pgAge"
        const val COLUMN_NAME_IMAGE_URL = "imageUrl"
        const val COLUMN_NAME_OVERVIEW = "overview"
        const val COLUMN_NAME_REVIEW_COUNT = "voteCount"
        const val COLUMN_NAME_IS_FAVORITE = "isFavorite"
    }

}