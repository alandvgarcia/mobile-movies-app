package com.alandvg.movies_app_test_involves.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["movieId", "genreId"],
    foreignKeys = [ForeignKey(
        entity = Movie::class,
        parentColumns = ["id"],
        childColumns = ["movieId"]
    ), ForeignKey(
        entity = Genre::class,
        parentColumns = ["id"],
        childColumns = ["genreId"]
    )
    ]
)
data class MovieGenreJoin(
    var movieId: Long,
    var genreId: Long
)