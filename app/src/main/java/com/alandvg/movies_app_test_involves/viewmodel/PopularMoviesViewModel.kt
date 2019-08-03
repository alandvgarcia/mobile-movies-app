package com.alandvg.movies_app_test_involves.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.alandvg.movies_app_test_involves.model.Movie
import com.alandvg.movies_app_test_involves.paging.PopularMovieDataSource
import com.alandvg.movies_app_test_involves.paging.PopularMovieDataSourceFactory
import com.alandvg.movies_app_test_involves.util.State

class PopularMoviesViewModel : ViewModel() {

    private val pageSize = 10
    lateinit var itensPagedList: LiveData<PagedList<Movie>>
    private lateinit var state: LiveData<State>
    private lateinit var popularMoviesDataSourceFactory: PopularMovieDataSourceFactory

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(true)
            .build()


        popularMoviesDataSourceFactory = PopularMovieDataSourceFactory()
        itensPagedList = LivePagedListBuilder(popularMoviesDataSourceFactory, config).build()

        state = Transformations.switchMap<PopularMovieDataSource,
                State>(popularMoviesDataSourceFactory.movieDataSourceLiveData, PopularMovieDataSource::state)


    }

    fun getState(): LiveData<State> = state


    fun listIsEmpty(): Boolean {
        return itensPagedList.value?.isEmpty() ?: true
    };

}
