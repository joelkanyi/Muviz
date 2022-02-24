package com.kanyideveloper.muviz.data.repository

import com.kanyideveloper.muviz.data.remote.responses.Credits
import com.kanyideveloper.muviz.data.remote.responses.MovieDetails
import com.kanyideveloper.muviz.data.remote.responses.TvSeriesDetails

interface FilmsDetailsRepository {
    // Movie details
    suspend fun getMoviesDetails(movieId: Int, language: String): MovieDetails
    // Movie Credits
    suspend fun getMoviesCredits(movieId: Int, language: String): Credits

    // TV Series details
    suspend fun getTvSeriesDetails(tvSeriesId: Int, language: String): TvSeriesDetails
    // TV Credits
    suspend fun getSeriesCredits(tvSeriesId: Int, language: String): Credits
}