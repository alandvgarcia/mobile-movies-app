package com.alandvg.movies_app_test_involves.model


import com.google.gson.annotations.SerializedName

data class SpokenLanguage(
    @SerializedName("iso_639_1")
    var iso6391: String? = null,
    @SerializedName("name")
    var name: String? = null
)