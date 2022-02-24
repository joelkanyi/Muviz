package com.kanyideveloper.muviz.data.repository

interface GenresRepository {
    suspend fun getMoviesGenres(language: String): GenresResponse
    suspend fun getTvSeriesGenres(language: String): GenresResponse
}