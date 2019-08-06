package com.alandvg.movies_app_test_involves.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Genre(
    @PrimaryKey
    @SerializedName("id")
    var id: Long? = null,
    @SerializedName("name")
    var name: String? = null
)