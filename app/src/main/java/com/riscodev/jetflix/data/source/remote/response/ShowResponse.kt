package com.riscodev.jetflix.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ShowResponse(

    @SerializedName("episode_run_time")
    var episodeRunTime: List<Int> = ArrayList(),

    @SerializedName("first_air_date")
    val firstAirDate: String = "",

    @SerializedName("genres")
    var genres: List<MovieResponse.GenresEntity> = ArrayList(),

    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("languages")
    var languages: List<String> = ArrayList(),

    @SerializedName("last_air_date")
    val lastAirDate: String = "",

    @SerializedName("name")
    val name: String = "",

    @SerializedName("number_of_episodes")
    var numberOfEpisodes: Int = 0,

    @SerializedName("number_of_seasons")
    var numberOfSeasons: Int = 0,

    @SerializedName("original_language")
    val originalLanguage: String = "",

    @SerializedName("original_name")
    val originalName: String = "",

    @SerializedName("overview")
    val overview: String = "",

    @SerializedName("popularity")
    val popularity: Double = 0.0,

    @SerializedName("poster_path")
    val posterPath: String = "",

    @SerializedName("status")
    val status: String = "",

    @SerializedName("tagline")
    val tagline: String = "",

    @SerializedName("vote_average")
    val voteAverage: Double = 0.0,

    @SerializedName("seasons")
    var seasonsEntity: List<SeasonsEntity> = ArrayList()
) {
    data class GenresEntity(
        @SerializedName("id")
        var id: Int = 0,
        @SerializedName("name")
        val name: String = ""
    )

    data class SeasonsEntity(
        @SerializedName("air_date")
        val airDate: String = "",

        @SerializedName("episode_count")
        var episodeCount: Int = 0,

        @SerializedName("id")
        var id: Int = 0,

        @SerializedName("name")
        val name: String = "",

        @SerializedName("overview")
        val overview: String = "",

        @SerializedName("poster_path")
        val posterPath: String = "",

        @SerializedName("season_number")
        var seasonNumber: Int = 0
    )

}
