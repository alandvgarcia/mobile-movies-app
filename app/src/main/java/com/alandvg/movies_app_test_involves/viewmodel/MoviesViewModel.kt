package com.alandvg.movies_app_test_involves.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.alandvg.movies_app_test_involves.model.Movie
import com.alandvg.movies_app_test_involves.paging.MoviesDataSource
import com.alandvg.movies_app_test_involves.paging.MoviesDataSourceFactory
import com.alandvg.movies_app_test_involves.util.State

class MoviesViewModel : ViewModel() {

    var itensPagedList: LiveData<PagedList<Movie>>? = null
    private var state: LiveData<State>? = null
    private lateinit var moviesDataSourceFactory: MoviesDataSourceFactory

    fun refresh() = moviesDataSourceFactory.movieDataSourceLiveData.value?.invalidate()

    fun getMovies(movieEndpoint: Int) {
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setPrefetchDistance(1)
            .setEnablePlaceholders(true)
            .build()

        moviesDataSourceFactory = MoviesDataSourceFactory(movieEndpoint)
        itensPagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()
        state = Transformations.switchMap<MoviesDataSource,
                State>(moviesDataSourceFactory.movieDataSourceLiveData, MoviesDataSource::state)

    }

    fun getState(): LiveData<State>? = state

    fun listIsEmpty(): Boolean {
        return itensPagedList?.value?.isEmpty() ?: true
    }
}
