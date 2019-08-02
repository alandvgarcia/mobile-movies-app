package com.alandvg.movies_app_test_involves.service

import com.alandvg.movies_app_test_involves.BuildConfig
import com.alandvg.movies_app_test_involves.model.Movie
import com.alandvg.movies_app_test_involves.model.ResultPaging
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieEndPoint {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.ApiKey
    ): Response<ResultPaging<List<Movie>>>

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.ApiKey
    ): Response<ResultPaging<List<Movie>>>
}