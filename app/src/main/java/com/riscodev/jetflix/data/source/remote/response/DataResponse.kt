package com.riscodev.jetflix.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DataResponse<T>(
    @SerializedName("page")
    val page: String,

    @SerializedName("results")
    val results: List<T>,

    @SerializedName("total_results")
    val totalResult: Int,

    @SerializedName("total_pages")
    val totalPages: Int,
)