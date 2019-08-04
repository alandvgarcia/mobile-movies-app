package com.alandvg.movies_app_test_involves.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.alandvg.movies_app_test_involves.model.Movie

class MoviesDataSourceFactory(val endPoint : Int) : DataSource.Factory<Int, Movie>() {
    val movieDataSourceLiveData = MutableLiveData<MoviesDataSource>()
    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MoviesDataSource(endPoint)
        movieDataSourceLiveData.postValue(movieDataSource)
        return movieDataSource
    }
}