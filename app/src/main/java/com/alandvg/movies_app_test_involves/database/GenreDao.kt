package com.alandvg.movies_app_test_involves.database

import androidx.paging.DataSource
import androidx.room.*
import com.alandvg.movies_app_test_involves.model.Genre
import com.alandvg.movies_app_test_involves.model.Movie

@Dao
interface GenreDao {
    @Query("SELECT * From genre")
    fun getAll(): DataSource.Factory<Int, Genre>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(genre: Genre)

    @Delete
    suspend fun delete(genre: Genre)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(genre: List<Genre>)

}