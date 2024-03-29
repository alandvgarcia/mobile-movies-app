package com.alandvg.movies_app_test_involves.model


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class Movie(
    @SerializedName("adult")
    var adult: Boolean? = null,
    @SerializedName("backdrop_path")
    var backdropPath: String? = null,
    @SerializedName("belongs_to_collection")
    @Ignore
    var belongsToCollection: BelongToCollection? = null,
    @ForeignKey(entity = BelongToCollection::class, parentColumns = ["id"], childColumns = ["belongsToCollectionId"])
    var belongsToCollectionId : Long? = null,
    @SerializedName("budget")
    var budget: Int? = null,
    @SerializedName("genres")
    @Ignore
    var genres: List<Genre>? = null,
    @SerializedName("homepage")
    var homepage: String? = null,
    @SerializedName("id")
    @PrimaryKey
    var id: Int? = null,
    @SerializedName("imdb_id")
    var imdbId: String? = null,
    @SerializedName("original_language")
    var originalLanguage: String? = null,
    @SerializedName("original_title")
    var originalTitle: String? = null,
    @SerializedName("overview")
    var overview: String? = null,
    @SerializedName("popularity")
    var popularity: Double? = null,
    @SerializedName("poster_path")
    var posterPath: String? = null,
    @SerializedName("production_companies")
    @Ignore
    var productionCompanies: List<ProductionCompany?>? = null,
    @SerializedName("production_countries")
    @Ignore
    var productionCountries: List<ProductionCountry?>? = null,
    @SerializedName("release_date")
    var releaseDate: String? = null,
    @SerializedName("revenue")
    var revenue: Int? = null,
    @SerializedName("runtime")
    var runtime: Int? = null,
    @SerializedName("spoken_languages")
    @Ignore
    var spokenLanguages: List<SpokenLanguage?>? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("tagline")
    var tagline: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("video")
    var video: Boolean? = null,
    @SerializedName("vote_average")
    var voteAverage: Double? = null,
    @SerializedName("vote_count")
    var voteCount: Int? = null
)