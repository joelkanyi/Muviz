package com.kanyideveloper.muviz.data.repository

import android.util.Log
import com.kanyideveloper.muviz.data.remote.TMDBApi
import com.kanyideveloper.muviz.data.remote.responses.*
import com.kanyideveloper.muviz.util.Resource
import timber.log.Timber
import javax.inject.Inject

class FilmsRepository @Inject constructor(private val api: TMDBApi) {
    // Movies
    suspend fun getPopularMovies(page: Int, language: String): Resource<MoviesResponse>{
        val response = try {
            api.getPopularMovies()
        }catch (e: Exception){
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Response: ${response.results}")
        return Resource.Success(response)
    }

    suspend fun getMoviesGenres(language: String): Resource<GenresResponse>{
        val response = try {
            api.getMovieGenres()
        }catch (e: Exception){
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Response: $response")
        return Resource.Success(response)
    }

    suspend fun getSeriesGenres(language: String): Resource<GenresResponse>{
        val response = try {
            api.getTvSeriesGenres()
        }catch (e: Exception){
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Response: $response")
        return Resource.Success(response)
    }

    suspend fun getPopularTvSeries(page: Int, language: String): Resource<TvSeriesResponse>{
        val response = try {
            api.getPopularTvSeries()
        }catch (e: Exception){
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Response: ${response.series}")
        return Resource.Success(response)
    }





/*    suspend fun getUpcomingMovies(page: Int, language: String): MoviesResponse
    suspend fun getNowPlayingMovies(page: Int, language: String): MoviesResponse
    suspend fun getTopRatedMovies(page: Int, language: String): MoviesResponse
    suspend fun getTrendingMovies(page: Int, language: String): MoviesResponse

    // TV Series
    suspend fun getTopRatedTvSeries(page: Int, language: String): TvSeriesResponse
    suspend fun getOnTheAirTvSeries(page: Int, language: String): TvSeriesResponse
    suspend fun getAiringTodayTvSeries(page: Int, language: String): TvSeriesResponse
    suspend fun getTrendingTvSeries(page: Int, language: String): TvSeriesResponse*/
}