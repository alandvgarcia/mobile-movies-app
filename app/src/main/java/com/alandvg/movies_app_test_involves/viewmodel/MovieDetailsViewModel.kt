package com.alandvg.movies_app_test_involves.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alandvg.movies_app_test_involves.model.Movie
import com.alandvg.movies_app_test_involves.repository.MovieRepository
import com.alandvg.movies_app_test_involves.service.MovieApiService
import com.alandvg.movies_app_test_involves.util.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MovieDetailsViewModel : ViewModel() {

    private val parentJob: Job = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.IO
    private val scope: CoroutineScope = CoroutineScope(coroutineContext)
    private val movie = MutableLiveData<Movie>()
    private val state = MutableLiveData<State>()


    fun loadMovie(idMovie: Long) {
        scope.launch(Dispatchers.IO) {
            state.postValue(State.LOADING)
            var movie: Movie?
            try {
                movie = MovieRepository(MovieApiService.movies()).getMovie(idMovie)
                movie?.also {
                    this@MovieDetailsViewModel.movie.postValue(it)
                    state.postValue(State.SUCCESS)
                } ?: run {
                    state.postValue(State.FAIL)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                state.postValue(State.FAIL)
            }

        }


    }

    fun getMovie(): LiveData<Movie> = movie
    fun getState(): LiveData<State> = state


}
