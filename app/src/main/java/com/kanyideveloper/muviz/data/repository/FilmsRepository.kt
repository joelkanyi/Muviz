package com.kanyideveloper.muviz.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kanyideveloper.muviz.data.paging.*
import com.kanyideveloper.muviz.data.remote.TMDBApi
import com.kanyideveloper.muviz.data.remote.responses.*
import com.kanyideveloper.muviz.model.Movie
import com.kanyideveloper.muviz.model.Search
import com.kanyideveloper.muviz.model.Series
import com.kanyideveloper.muviz.util.Resource
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class FilmsRepository @Inject constructor(private val api: TMDBApi) {
    // Movies
    fun getTrendingMoviesThisWeek(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 27),
            pagingSourceFactory = {
                TrendingMoviesSource(api)
            }
        ).flow
    }

    fun getUpcomingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 27),
            pagingSourceFactory = {
                UpcomingMoviesSource(api)
            }
        ).flow
    }

    fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 27),
            pagingSourceFactory = {
                TopRatedMoviesSource(api)
            }
        ).flow
    }

    fun getNowPlayingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 27),
            pagingSourceFactory = {
                NowPlayingMoviesSource(api)
            }
        ).flow
    }

    fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 27),
            pagingSourceFactory = {
                PopularMoviesSource(api)
            }
        ).flow
    }

    suspend fun getMoviesDetails(movieId: Int): Resource<MovieDetails> {
        val response = try {
            api.getMovieDetails(movieId)
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Movie details: $response")
        return Resource.Success(response)
    }

    suspend fun getMoviesGenres(): Resource<GenresResponse> {
        val response = try {
            api.getMovieGenres()
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Movies genres: $response")
        return Resource.Success(response)
    }

    suspend fun getMovieCasts(movieId: Int): Resource<CreditsResponse> {
        val response = try {
            api.getMovieCredits(movieId)
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }

        Timber.d("Movie Casts ${response.toString()}")
        return Resource.Success(response)
    }


    //.....................................................................................//
    // Tv Series
    fun getTrendingThisWeekTvSeries(): Flow<PagingData<Series>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 27),
            pagingSourceFactory = {
                TrendingSeriesSource(api)
            }
        ).flow
    }

    fun getOnTheAirTvSeries(): Flow<PagingData<Series>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 27),
            pagingSourceFactory = {
                OnTheAirSeriesSource(api)
            }
        ).flow
    }

    fun getTopRatedTvSeries(): Flow<PagingData<Series>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 27),
            pagingSourceFactory = {
                TopRatedSeriesSource(api)
            }
        ).flow
    }

    fun getAiringTodayTvSeries(): Flow<PagingData<Series>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 27),
            pagingSourceFactory = {
                AiringTodayTvSeriesSource(api)
            }
        ).flow
    }

    fun getPopularTvSeries(): Flow<PagingData<Series>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 27),
            pagingSourceFactory = {
                PopularSeriesSource(api)
            }
        ).flow
    }

    suspend fun getTvSeriesDetails(tvId: Int): Resource<TvSeriesDetails> {
        val response = try {
            api.getTvSeriesDetails(tvId)
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Series details: $response")
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

    suspend fun getTvSeriesCasts(tvId: Int): Resource<CreditsResponse> {
        val response = try {
            api.getTvSeriesCredits(tvId)
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }

        Timber.d("Series casts $response")
        return Resource.Success(response)
    }

    fun multiSearch(queryParam: String): Flow<PagingData<Search>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 27),
            pagingSourceFactory = {
                SearchPagingSource(api, queryParam)
            }
        ).flow
    }
}