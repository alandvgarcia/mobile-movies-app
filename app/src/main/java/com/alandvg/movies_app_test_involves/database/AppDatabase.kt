package com.alandvg.movies_app_test_involves.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alandvg.movies_app_test_involves.model.*

@Database(
    entities = [
        Movie::class,
        BelongToCollection::class,
        Genre::class,
        MovieGenreJoin::class,
        MovieProdCompanyJoin::class,
        MovieProdCountryJoin::class,
        ProductionCountry::class,
        ProductionCompany::class,
        MovieSpokenLanguageJoin::class,
        SpokenLanguage::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var db: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (db == null) {
                db = Room.databaseBuilder(context, AppDatabase::class.java, "movies").build()
            }
            return db!!
        }
    }

    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao
}