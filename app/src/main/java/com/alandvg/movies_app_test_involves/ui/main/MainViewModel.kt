package com.alandvg.movies_app_test_involves.ui.main

import androidx.lifecycle.ViewModel
import com.alandvg.movies_app_test_involves.BuildConfig
import com.alandvg.movies_app_test_involves.repository.MovieRepository
import com.alandvg.movies_app_test_involves.service.MovieApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    init {

        GlobalScope.launch(Dispatchers.IO) {
            val movies = MovieRepository(MovieApiService.movies()).getPopularMovies()
            println(BuildConfig.ApiKey)
            println(movies?.results.toString())

            val moviesTopRated = MovieRepository(MovieApiService.movies()).getTopRatedMovies()
            println(moviesTopRated?.results.toString())

        }

    }

}
