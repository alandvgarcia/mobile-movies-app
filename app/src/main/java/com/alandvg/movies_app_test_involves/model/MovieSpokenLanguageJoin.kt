package com.alandvg.movies_app_test_involves.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["movieId", "spokenLanguageId"],
    foreignKeys = [ForeignKey(
        entity = Movie::class,
        parentColumns = ["id"],
        childColumns = ["movieId"]
    ), ForeignKey(
        entity = ProductionCountry::class,
        parentColumns = ["id"],
        childColumns = ["spokenLanguageId"]
    )
    ]
)
data class MovieSpokenLanguageJoin(
    var movieId: Long,
    var spokenLanguageId: Long
)