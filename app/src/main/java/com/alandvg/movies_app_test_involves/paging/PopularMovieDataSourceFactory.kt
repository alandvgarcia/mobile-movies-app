package com.alandvg.movies_app_test_involves.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.alandvg.movies_app_test_involves.model.Movie

class PopularMovieDataSourceFactory : DataSource.Factory<Int, Movie>() {
    val movieDataSourceLiveData = MutableLiveData<PopularMovieDataSource>()
    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = PopularMovieDataSource()
        movieDataSourceLiveData.postValue(movieDataSource)
        return movieDataSource
    }
}