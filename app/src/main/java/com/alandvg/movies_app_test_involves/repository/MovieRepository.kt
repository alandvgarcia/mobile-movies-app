package com.alandvg.movies_app_test_involves.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.alandvg.movies_app_test_involves.database.AppDatabase
import com.alandvg.movies_app_test_involves.model.Movie
import com.alandvg.movies_app_test_involves.model.ResultPaging
import com.alandvg.movies_app_test_involves.service.MovieEndPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private val TAG = "MovieRepository"

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


    suspend fun getUpcomingMovie(page: Int = 1): ResultPaging<List<Movie>>? {
        return safeApiCall(
                call = { apiInterface.getUpComingMovie(page) },
                error = "Error  get upcoming movies"
        )
    }

    suspend fun searchMovies(page: Int = 1, search: String = ""): ResultPaging<List<Movie>>? {
        return safeApiCall(
                call = { apiInterface.searchMovies(page, search) },
                error = "Error  search movies"
        )
    }

    suspend fun getMovie(id: Long): Movie? {
        return safeApiCall(
                call = { apiInterface.getMovie(id) },
                error = "Error  get  movie"
        )
    }


    suspend fun getSaved(context: Context, size: Int): LiveData<PagedList<Movie>> {
        return AppDatabase.getInstance(context).movieDao().getAll().toLiveData(size)
    }

    suspend fun saveMovie(context: Context, movie: Movie) {
        try {
            Log.d("Movie", movie.toString())
            AppDatabase.getInstance(context).movieDao().insert(movie)
            movie.genres?.also { listGenres ->
                listGenres.forEach { genre ->
                    genre.also {
                        AppDatabase.getInstance(context).genreDao().insert(it)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }
}