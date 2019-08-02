package com.alandvg.movies_app_test_involves.repository

import com.alandvg.movies_app_test_involves.model.Movie
import com.alandvg.movies_app_test_involves.model.ResultPaging
import com.alandvg.movies_app_test_involves.service.MovieEndPoint

class MovieRepository(private val apiInterface: MovieEndPoint) : BaseRepository() {

    suspend fun getPopularMovies(page: Int = 1): ResultPaging<List<Movie>>? {
        return safeApiCall(
            call = { apiInterface.getPopularMovies(page) },
            error = "Error  get popular movies"
        )
    }

    suspend fun getTopRatedMovies(page: Int = 1): ResultPaging<List<Movie>>? {
        return safeApiCall(
            call = { apiInterface.getTopRatedMovies(page) },
            error = "Error  get top rated movies"
        )
    }

    suspend fun getMovie(id: Long): Movie? {
        return safeApiCall(
            call = { apiInterface.getMovie(id) },
            error = "Error  get  movie"
        )
    }
}