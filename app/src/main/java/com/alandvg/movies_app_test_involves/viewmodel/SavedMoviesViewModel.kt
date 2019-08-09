package com.alandvg.movies_app_test_involves.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.alandvg.movies_app_test_involves.database.AppDatabase
import com.alandvg.movies_app_test_involves.database.MovieDao
import com.alandvg.movies_app_test_involves.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SavedMoviesViewModel(application: Application) : AndroidViewModel(application) {


    val movieDao = AppDatabase.getInstance(application).movieDao()
    private val parentJob: Job = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.IO
    private val scope: CoroutineScope = CoroutineScope(coroutineContext)


    val movieList: LiveData<PagedList<Movie>> =
        movieDao.getAll().toLiveData(pageSize = 10)



    fun removeMovie(movie: Movie) {
        scope.launch {
            movieDao.delete(movie)
        }
    }


}