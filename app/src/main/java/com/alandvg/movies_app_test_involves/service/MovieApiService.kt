package com.alandvg.movies_app_test_involves.service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal object MovieApiService {

    val TAG = this@MovieApiService.javaClass.simpleName
    val URL_API = "https://api.themoviedb.org/3/"

    private fun provideRetrofit(baseUrl: String): Retrofit {

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    internal fun movies() = provideRetrofit(URL_API).create(MovieEndPoint::class.java)

}