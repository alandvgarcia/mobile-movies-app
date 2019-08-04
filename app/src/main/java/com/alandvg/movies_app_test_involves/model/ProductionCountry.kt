package com.alandvg.movies_app_test_involves.model


import com.google.gson.annotations.SerializedName

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    var iso31661: String? = null,
    @SerializedName("name")
    var name: String? = null
)