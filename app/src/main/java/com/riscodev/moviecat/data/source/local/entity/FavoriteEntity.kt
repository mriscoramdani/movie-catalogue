package com.riscodev.moviecat.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteentities")
class FavoriteEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "contentId")
    val contentId: String,

    @ColumnInfo(name = "contentType")
    val contentType: String,
)
