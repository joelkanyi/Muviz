package com.kanyideveloper.muviz.data.remote.dto

data class TvSeriesResponse(
    val page: Int,
    val seriesDtos: List<SeriesDto>,
    val total_pages: Int,
    val total_results: Int
)