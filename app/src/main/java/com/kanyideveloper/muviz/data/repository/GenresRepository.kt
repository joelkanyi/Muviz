package com.kanyideveloper.muviz.data.repository

import com.kanyideveloper.muviz.data.remote.TMDBApi
import com.kanyideveloper.muviz.data.remote.responses.GenresResponse
import com.kanyideveloper.muviz.util.Resource
import timber.log.Timber
import javax.inject.Inject

class GenresRepository @Inject constructor(private val api: TMDBApi) {
    suspend fun getMoviesGenres(): Resource<GenresResponse> {
        val response = try {
            api.getMovieGenres()
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Movies genres: $response")
        return Resource.Success(response)
    }

    suspend fun getSeriesGenres(): Resource<GenresResponse> {
        val response = try {
            api.getTvSeriesGenres()
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Series genres: $response")
        return Resource.Success(response)
    }
}
