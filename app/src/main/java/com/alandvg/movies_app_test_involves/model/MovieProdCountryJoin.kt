package com.alandvg.movies_app_test_involves.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["movieId", "productionCountryId"],
    foreignKeys = [ForeignKey(
        entity = Movie::class,
        parentColumns = ["id"],
        childColumns = ["movieId"]
    ), ForeignKey(
        entity = ProductionCountry::class,
        parentColumns = ["id"],
        childColumns = ["productionCountryId"]
    )
    ]
)
data class MovieProdCountryJoin(
    var movieId: Long,
    var productionCountryId: Long
)