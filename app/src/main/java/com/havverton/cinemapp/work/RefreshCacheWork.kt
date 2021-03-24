package com.havverton.cinemapp.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.havverton.cinemapp.MovieBuilderProvider
import com.havverton.cinemapp.db.AppDatabase
import com.havverton.cinemapp.model.Movie

class RefreshCacheWork(context: Context, params:WorkerParameters): CoroutineWorker(context,params) {

    override suspend fun doWork(): Result {
        val moviesList:MutableList<Movie> = mutableListOf()
        var isFull = false
        moviesList.addAll(MovieBuilderProvider.getMovieList())
            if(moviesList.isNotEmpty()){isFull = true}
        return if(isFull){
            AppDatabase.create(applicationContext).movieDao.updateAll(moviesList)
            Result.success()
        }else {
            Result.retry()
        }


}
}