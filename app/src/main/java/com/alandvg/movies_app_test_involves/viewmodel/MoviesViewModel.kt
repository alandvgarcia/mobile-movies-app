package com.alandvg.movies_app_test_involves.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.alandvg.movies_app_test_involves.database.AppDatabase
import com.alandvg.movies_app_test_involves.model.Movie
import com.alandvg.movies_app_test_involves.paging.MoviesDataSource
import com.alandvg.movies_app_test_involves.paging.MoviesDataSourceFactory
import com.alandvg.movies_app_test_involves.util.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MoviesViewModel(application: Application) : AndroidViewModel(application) {

    var itensPagedList: LiveData<PagedList<Movie>>? = null
    private var state: LiveData<State>? = null
    private lateinit var moviesDataSourceFactory: MoviesDataSourceFactory

    private val parentJob: Job = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.IO
    private val scope: CoroutineScope = CoroutineScope(coroutineContext)

    fun refresh() = moviesDataSourceFactory.movieDataSourceLiveData.value?.invalidate()

    fun getMovies(movieEndpoint: Int, search : String = "") {
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setPrefetchDistance(1)
            .setEnablePlaceholders(true)
            .build()

        moviesDataSourceFactory = MoviesDataSourceFactory(movieEndpoint,search)
        itensPagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()
        state = Transformations.switchMap<MoviesDataSource,
                State>(moviesDataSourceFactory.movieDataSourceLiveData, MoviesDataSource::state)

    }

    fun getState(): LiveData<State>? = state

    fun listIsEmpty(): Boolean {
        return itensPagedList?.value?.isEmpty() ?: true
    }

    fun saveMovie(movie: Movie) {

        scope.launch(Dispatchers.IO) {
            AppDatabase.getInstance(getApplication()).movieDao().insert(movie)

            val movies = AppDatabase.getInstance(getApplication()).movieDao().getAll()

            movies.forEach {
                Log.d("Movie", it.toString())
            }
        }

    }
}
