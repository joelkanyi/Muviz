package com.kanyideveloper.muviz.data.repository

import com.kanyideveloper.muviz.data.remote.responses.CreditsResponse
import com.kanyideveloper.muviz.data.remote.responses.MovieDetails
import com.kanyideveloper.muviz.data.remote.responses.TvSeriesDetails

interface FilmsDetailsRepository {
    // Movie details
    suspend fun getMoviesDetails(movieId: Int, language: String): MovieDetails
    // Movie CreditsResponse
    suspend fun getMoviesCredits(movieId: Int, language: String): CreditsResponse

    // TV Series details
    suspend fun getTvSeriesDetails(tvSeriesId: Int, language: String): TvSeriesDetails
    // TV CreditsResponse
    suspend fun getSeriesCredits(tvSeriesId: Int, language: String): CreditsResponse
}