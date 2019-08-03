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

class PopularMovieDataSource : PageKeyedDataSource<Int, Movie>(){

    private val movieRepository = MovieRepository(MovieApiService.movies())
    private val parentJob: Job = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.IO
    private val scope: CoroutineScope = CoroutineScope(coroutineContext)
    var state = MutableLiveData<State>()


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        scope.launch(Dispatchers.IO) {
            updateState(State.LOADING)
            try {
                val movies = movieRepository.getPopularMovies(1)
                movies?.also {
                    callback.onResult(it.results, null, 1)
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
        scope.launch(Dispatchers.IO) {
            updateState(State.LOADING)
            try {
                val movies = movieRepository.getPopularMovies(params.key)
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
            this@PopularMovieDataSource.state.postValue(state)
        }

    }

}