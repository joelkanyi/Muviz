package com.kanyideveloper.muviz.data.remote.dto

data class MoviesResponse(
    val page: Int,
    val movieDtos: List<MovieDto>,
    val total_pages: Int,
    val total_results: Int
)