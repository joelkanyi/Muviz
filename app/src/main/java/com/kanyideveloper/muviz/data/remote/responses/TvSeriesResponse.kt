package com.kanyideveloper.muviz.data.remote.responses

data class TvSeriesResponse(
    val page: Int,
    val series: List<Series>,
    val total_pages: Int,
    val total_results: Int
)