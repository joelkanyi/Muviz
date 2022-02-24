package com.kanyideveloper.muviz.data.repository

import com.kanyideveloper.muviz.data.remote.TMDBApi
import com.kanyideveloper.muviz.data.remote.responses.MoviesResponse
import com.kanyideveloper.muviz.data.remote.responses.TvSeriesResponse
import com.kanyideveloper.muviz.util.Resource
import javax.inject.Inject

class FilmsRepository @Inject constructor(private val api: TMDBApi) {
    // Movies
    suspend fun getPopularMovies(page: Int, language: String): Resource<MoviesResponse>{
        val response = try {
            api.getPopularMovies()
        }catch (e: Exception){
            return Resource.Error("Unknown error occurred")
        }

        return Resource.Success(response)
    }




/*    suspend fun getUpcomingMovies(page: Int, language: String): MoviesResponse
    suspend fun getNowPlayingMovies(page: Int, language: String): MoviesResponse
    suspend fun getTopRatedMovies(page: Int, language: String): MoviesResponse
    suspend fun getTrendingMovies(page: Int, language: String): MoviesResponse

    // TV Series
    suspend fun getTopRatedTvSeries(page: Int, language: String): TvSeriesResponse
    suspend fun getOnTheAirTvSeries(page: Int, language: String): TvSeriesResponse
    suspend fun getPopularTvSeries(page: Int, language: String): TvSeriesResponse
    suspend fun getAiringTodayTvSeries(page: Int, language: String): TvSeriesResponse
    suspend fun getTrendingTvSeries(page: Int, language: String): TvSeriesResponse*/
}