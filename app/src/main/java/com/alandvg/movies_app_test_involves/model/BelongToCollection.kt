package com.alandvg.movies_app_test_involves.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class BelongToCollection(
    @SerializedName("backdrop_path")
    var backdropPath: String? = null,
    @SerializedName("id")
    @PrimaryKey
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("poster_path")
    var posterPath: String? = null
)