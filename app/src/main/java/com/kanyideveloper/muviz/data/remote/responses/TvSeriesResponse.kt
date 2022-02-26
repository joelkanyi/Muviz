package com.kanyideveloper.muviz.data.remote.responses

import com.google.gson.annotations.SerializedName

data class TvSeriesResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val series: List<Series>,
    @SerializedName("total_pages")
    val total_pages: Int,
    @SerializedName("total_results")
    val total_results: Int
)