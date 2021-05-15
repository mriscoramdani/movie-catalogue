package com.riscodev.moviecat.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "showentities")
class ShowEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "showId")
    var showId: String,

    @ColumnInfo(name = "originalName")
    val originalName: String,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "firstAirDate")
    val firstAirDate: String,

    @ColumnInfo(name = "voteAverage")
    val voteAverage: Double,

    @ColumnInfo(name = "status")
    val status: String,

    @ColumnInfo(name = "genres")
    val genres: String,

    @ColumnInfo(name = "numberSeasons")
    val numberSeasons: Int,

    @ColumnInfo(name = "posterPath")
    val posterPath: String,
) {
    companion object {
        const val TYPE = "show"
    }
}