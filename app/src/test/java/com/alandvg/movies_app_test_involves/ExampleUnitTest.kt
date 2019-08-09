package com.alandvg.movies_app_test_involves

import com.alandvg.movies_app_test_involves.model.Movie
import com.alandvg.movies_app_test_involves.model.ResultPaging
import com.alandvg.movies_app_test_involves.repository.MovieRepository
import com.alandvg.movies_app_test_involves.service.MovieApiService
import com.alandvg.movies_app_test_involves.service.MovieEndPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {


    @Mock
    val movieEndPoint = MovieApiService.movies()
    private lateinit var  movieRepository : MovieRepository


    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        movieRepository = MovieRepository(movieEndPoint)
    }


    @Test
    fun `call endpoint popular movies`() {

        var movies : ResultPaging<List<Movie>>? = null

        GlobalScope.launch(Dispatchers.IO) {
            try {
                movies = movieRepository.getPopularMovies(1)
                println(movies.toString())

            }catch (e : Exception){
                movies = null
            }

            assertEquals(movies != null, movies)
        }



    }
}