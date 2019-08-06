package com.alandvg.movies_app_test_involves.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["movieId", "productionCompanyId"],
    foreignKeys = [ForeignKey(
        entity = Movie::class,
        parentColumns = ["id"],
        childColumns = ["movieId"]
    ), ForeignKey(
        entity = ProductionCompany::class,
        parentColumns = ["id"],
        childColumns = ["productionCompanyId"]
    )
    ]
)
data class MovieProdCompanyJoin(
    var movieId: Long,
    var productionCompanyId: Long
)