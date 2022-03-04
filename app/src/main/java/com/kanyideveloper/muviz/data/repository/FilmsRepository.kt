package com.kanyideveloper.muviz.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kanyideveloper.muviz.data.paging.TrendingMoviesSource
import com.kanyideveloper.muviz.data.remote.TMDBApi
import com.kanyideveloper.muviz.data.remote.responses.*
import com.kanyideveloper.muviz.data.remote.responses.debug.MultiSearchResponse
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
/*        val response = try {
            api.getTrendingMovies()
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Trending movies: ${response.results}")
        return Resource.Success(response)*/
    }

    /*
    *     fun getCharacter() : Flow<PagingData<CharacterData>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 30),
            pagingSourceFactory = {
                CharacterPagingSource(retrofitService)
            }
        ).flow
    }*/

    suspend fun getUpcomingMovies(page: Int, language: String): Resource<MoviesResponse> {
        val response = try {
            api.getUpcomingMovies()
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Upcoming movies: ${response.results}")
        return Resource.Success(response)
    }

    suspend fun getTopRatedMovies(page: Int, language: String): Resource<MoviesResponse> {
        val response = try {
            api.getTopRatedMovies()
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Top rated movies: ${response.results}")
        return Resource.Success(response)
    }

    suspend fun getNowPlayingMovies(page: Int, language: String): Resource<MoviesResponse> {
        val response = try {
            api.getNowPlayingMovies()
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Now playing movies: ${response.results}")
        return Resource.Success(response)
    }

    suspend fun getPopularMovies(page: Int, language: String): Resource<MoviesResponse> {
        val response = try {
            api.getPopularMovies()
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Popular movies: ${response.results}")
        return Resource.Success(response)
    }

    suspend fun getMoviesDetails(movieId: Int, language: String = "en"): Resource<MovieDetails> {
        val response = try {
            api.getMovieDetails(movieId)
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Movie details: $response")
        return Resource.Success(response)
    }

    suspend fun getMoviesGenres(language: String): Resource<GenresResponse> {
        val response = try {
            api.getMovieGenres()
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Movies genres: $response")
        return Resource.Success(response)
    }

    suspend fun getMovieCasts(movieId: Int): Resource<Credits> {
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
    suspend fun getTrendingThisWeekTvSeries(
        page: Int,
        language: String
    ): Resource<TvSeriesResponse> {
        val response = try {
            api.getTrendingTvSeries()
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Trending series: ${response.series}")
        return Resource.Success(response)
    }

    suspend fun getOnTheAirTvSeries(page: Int, language: String): Resource<TvSeriesResponse> {
        val response = try {
            api.getOnTheAirTvSeries()
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("On air series: ${response.series}")
        return Resource.Success(response)
    }

    suspend fun getTopRatedTvSeries(page: Int, language: String): Resource<TvSeriesResponse> {
        val response = try {
            api.getTopRatedTvSeries()
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Top rated series: ${response.series}")
        return Resource.Success(response)
    }

    suspend fun getAiringTodayTvSeries(page: Int, language: String): Resource<TvSeriesResponse> {
        val response = try {
            api.getAiringTodayTvSeries()
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Airing today series: ${response.series}")
        return Resource.Success(response)
    }

    suspend fun getPopularTvSeries(page: Int, language: String): Resource<TvSeriesResponse> {
        val response = try {
            api.getPopularTvSeries()
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Popular series: ${response.series}")
        return Resource.Success(response)
    }

    suspend fun getTvSeriesDetails(tvId: Int, language: String = "en"): Resource<TvSeriesDetails> {
        val response = try {
            api.getTvSeriesDetails(tvId)
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Series details: $response")
        return Resource.Success(response)
    }

    suspend fun getSeriesGenres(language: String): Resource<GenresResponse> {
        val response = try {
            api.getTvSeriesGenres()
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Series genres: $response")
        return Resource.Success(response)
    }

    suspend fun getTvSeriesCasts(tvId: Int): Resource<Credits> {
        val response = try {
            api.getTvSeriesCredits(tvId)
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }

        Timber.d("Series casts ${response.toString()}")
        return Resource.Success(response)
    }

    suspend fun multiSearch(queryParam: String): Resource<SearchMovieResponse> {
        val response = try {
            api.multiSearch(queryParam)
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }

        Timber.d("Series casts ${response.results}")
        return Resource.Success(response)
    }

    suspend fun searchAll(queryParam: String) : Resource<MultiSearchResponse>{
        val response = try {
            api.searchAll(queryParam)
        }catch (e: Exception){
            return Resource.Error(e.localizedMessage)
        }
        Timber.d("All Searches result: ${response.results}")
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