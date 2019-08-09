package com.alandvg.movies_app_test_involves.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alandvg.movies_app_test_involves.model.Movie
import com.alandvg.movies_app_test_involves.repository.MovieRepository
import com.alandvg.movies_app_test_involves.service.MovieApiService
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


    fun loadMovie(idMovie: Long) {

        scope.launch(Dispatchers.IO) {

            var movie: Movie? = null
            try {
                movie = MovieRepository(MovieApiService.movies()).getMovie(idMovie)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            movie?.also {
                this@MovieDetailsViewModel.movie.postValue(it)
            }
        }


    }

    fun getMovie() : LiveData<Movie> = movie


}
