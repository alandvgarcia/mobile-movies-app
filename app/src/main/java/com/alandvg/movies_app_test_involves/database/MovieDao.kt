package com.alandvg.movies_app_test_involves.database

import androidx.paging.DataSource
import androidx.room.*
import com.alandvg.movies_app_test_involves.model.Movie

@Dao
interface MovieDao {
    @Query("SELECT * From movie")
    fun getAll(): DataSource.Factory<Int, Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)

}