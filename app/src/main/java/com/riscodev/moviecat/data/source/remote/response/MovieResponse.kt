package com.riscodev.moviecat.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(

    @SerializedName("genres")
    var genresEntity: List<GenresEntity> = ArrayList(),

    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("original_language")
    val originalLanguange: String = "",

    @SerializedName("original_title")
    val originalTitle: String = "",

    @SerializedName("overview")
    val overview: String = "",

    @SerializedName("popularity")
    val popularity: Double = 0.0,

    @SerializedName("poster_path")
    val posterPath: String = "",

    @SerializedName("release_date")
    val releaseDate: String = "",

    @SerializedName("runtime")
    var runtime: Int = 0,

    @SerializedName("status")
    val status: String = "",

    @SerializedName("tagline")
    val tagline: String = "",

    @SerializedName("title")
    val title: String = "",

    @SerializedName("vote_average")
    val voteAverage: Double = 0.0
) {
    data class GenresEntity(
        @SerializedName("id")
        var id: Int = 0,
        @SerializedName("name")
        val name: String = ""
    )
}

