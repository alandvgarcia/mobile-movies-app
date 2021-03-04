package com.alandvg.movies_app_test_involves.service

import com.alandvg.movies_app_test_involves.util.CacheDirUtil
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


internal object MovieApiService {

    val TAG = this@MovieApiService.javaClass.simpleName
    val URL_API = "https://api.themoviedb.org/3/"

    private fun provideRetrofit(baseUrl: String): Retrofit {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val httpCacheDirectory = File(CacheDirUtil.cacheDir, "http-cache")
        val cache = Cache(httpCacheDirectory, cacheSize.toLong())

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY


        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .retryOnConnectionFailure(true)
            .cache(cache)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    internal fun movies() = provideRetrofit(URL_API).create(MovieEndPoint::class.java)




}

