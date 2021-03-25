package com.havverton.cinemapp.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.havverton.cinemapp.db.MovieListContract


@Entity(
    tableName = MovieListContract.Movies.TABLE_NAME,
    indices = [Index(MovieListContract.Movies.COLUMN_NAME_ID)]
)
data class Movie(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = MovieListContract.Movies.COLUMN_NAME_ID)
    val id:Long = 0,
    @ColumnInfo(name = MovieListContract.Movies.COLUMN_NAME_TITLE)
    val title:String,
    @ColumnInfo(name = MovieListContract.Movies.COLUMN_NAME_GENRES)
    val genres:String,
    @ColumnInfo(name = MovieListContract.Movies.COLUMN_NAME_AGE)
    val pgAge: Int,
    @ColumnInfo(name = MovieListContract.Movies.COLUMN_NAME_IMAGE_URL)
    val imageUrl: String,
    @ColumnInfo(name = MovieListContract.Movies.COLUMN_NAME_OVERVIEW)
    val overview: String,
    @ColumnInfo(name = MovieListContract.Movies.COLUMN_NAME_REVIEW_COUNT)
    val voteCount: Long,
    @ColumnInfo(name = MovieListContract.Movies.COLUMN_NAME_IS_FAVORITE)
    var isFavorite: Boolean

)