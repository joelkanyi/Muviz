package com.kanyideveloper.muviz.data.remote.responses

data class MoviesResponse(
    val page: Int,
    val movies: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)