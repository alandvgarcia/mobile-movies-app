package com.alandvg.movies_app_test_involves.service

import com.alandvg.movies_app_test_involves.BuildConfig
import com.alandvg.movies_app_test_involves.model.Movie
import com.alandvg.movies_app_test_involves.model.ResultPaging
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieEndPoint {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.ApiKey
    ): Response<ResultPaging<List<Movie>>>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.ApiKey
    ): Response<ResultPaging<List<Movie>>>


    @GET("movie/upcoming")
    suspend fun getUpComingMovie(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.ApiKey
    ): Response<ResultPaging<List<Movie>>>


    @GET("movie/{id}")
    suspend fun getMovie(
        @Path("id") page: Long,
        @Query("api_key") apiKey: String = BuildConfig.ApiKey
    ): Response<Movie>


    @GET("search/movie")
    suspend fun searchMovies(
        @Query("page") page: Int,
        @Query("query") search: String,
        @Query("api_key") apiKey: String = BuildConfig.ApiKey
    ): Response<ResultPaging<List<Movie>>>
}