package com.alandvg.movies_app_test_involves.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.alandvg.movies_app_test_involves.model.Movie
import com.alandvg.movies_app_test_involves.repository.MovieRepository
import com.alandvg.movies_app_test_involves.service.MovieApiService
import com.alandvg.movies_app_test_involves.util.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MoviesDataSource(val endPoint : Int) : PageKeyedDataSource<Int, Movie>(){


    companion object{
        val POPULAR = 1
        val TOP_RATED = 2
        val UPCOMING = 3
    }

    private val movieRepository = MovieRepository(MovieApiService.movies())
    private val parentJob: Job = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.IO
    private val scope: CoroutineScope = CoroutineScope(coroutineContext)
    var state = MutableLiveData<State>()


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        scope.launch {
            updateState(State.LOADING)
            try {
                val movies = when(endPoint){
                    TOP_RATED -> movieRepository.getTopRatedMovies(1)
                    UPCOMING -> movieRepository.getUpcomingMovie(1)
                    else -> movieRepository.getPopularMovies(1)
                }

                movies?.also {
                    callback.onResult(it.results, null, 2)
                    updateState(State.SUCCESS)
                }?:run{
                    updateState(State.FAIL)
                }
            }catch (e : Exception){
                updateState(State.FAIL)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        scope.launch {
            updateState(State.LOADING)
            try {
                val movies = when(endPoint){
                    TOP_RATED -> movieRepository.getTopRatedMovies(params.key)
                    UPCOMING -> movieRepository.getUpcomingMovie(params.key)
                    else -> movieRepository.getPopularMovies(params.key)
                }
                movies?.also {
                    callback.onResult(it.results, params.key+1)
                    updateState(State.SUCCESS)
                }?:run{
                    updateState(State.FAIL)
                }
            }catch (e : Exception){
                updateState(State.FAIL)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {}

    private fun updateState(state: State) {
        scope.launch(Dispatchers.IO) {
            this@MoviesDataSource.state.postValue(state)
        }

    }

}