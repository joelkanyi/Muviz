package com.kanyideveloper.muviz.data.remote.responses

data class Series(
    val backdrop_path: String,
    val first_air_date: String,
    val genreIds: List<Int>,
    val id: Int,
    val name: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val vote_average: Double,
    val vote_count: Int
)