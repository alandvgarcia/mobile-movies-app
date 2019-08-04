package com.alandvg.movies_app_test_involves.model


import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null
)