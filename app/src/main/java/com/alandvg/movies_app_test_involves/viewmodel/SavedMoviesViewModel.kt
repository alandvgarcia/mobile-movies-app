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

class SavedMoviesViewModel(application: Application) : AndroidViewModel(application) {

    val movieDao = AppDatabase.getInstance(application).movieDao()

    val movieList: LiveData<PagedList<Movie>> =
        movieDao.getAll().toLiveData(pageSize = 10)


}