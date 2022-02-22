package com.kanyideveloper.muviz.data.remote.dto

data class MovieDetailsResponse(
    val adult: Boolean,
    val backdrop_path: String,
    val belongs_to_collection: Any,
    val budget: Int,
    val genreDtos: List<GenreDto>,
    val homepage: String,
    val id: Int,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companyDtos: List<ProductionCompanyDto>,
    val production_countryDtos: List<ProductionCountryDto>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val spoken_languageDtos: List<SpokenLanguageDto>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)