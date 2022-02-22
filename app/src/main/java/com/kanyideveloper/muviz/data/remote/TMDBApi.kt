package com.kanyideveloper.muviz.data.remote

import com.kanyideveloper.muviz.data.remote.dto.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {
    /*@GET("configuration")
    fun getConfig(): Call<Config>*/

    /*  @GET("discover/movie")
    suspend fun discoverMovies(
         @Query("page") page: Int,
         @Query("language") isoCode: String,
         @Query("sort_by") type: SortTypeParam,
         @Query("with_genres") genreDtos: GenresParam,
         @FloatRange(from = 0.0)
         @Query("vote_average.gte")
         voteAverageMin: Float,
         @FloatRange(from = 0.0)
         @Query("vote_average.lte")
         voteAverageMax: Float,
         @Query("release_date.gte")
         fromReleaseDate: DateParam?,
         @Query("release_date.lte")
         toReleaseDate: DateParam?
     ): MoviesResponse*/

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("language") isoCode: String
    ): MoviesResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
        @Query("language") isoCode: String
    ): MoviesResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("language") isoCode: String
    ): MoviesResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
        @Query("language") isoCode: String
    ): MoviesResponse

    @GET("tv/top_rated")
    suspend fun getTopRatedTvSeries(
        @Query("page") page: Int,
        @Query("language") isoCode: String
    ): TvSeriesResponse

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTvSeries(
        @Query("page") page: Int,
        @Query("language") isoCode: String
    ): TvSeriesResponse

    @GET("tv/popular")
    suspend fun getPopularTvSeries(
        @Query("page") page: Int,
        @Query("language") isoCode: String
    ): TvSeriesResponse

    @GET("tv/airing_today")
    suspend fun getAiringTodayTvSeries(
        @Query("page") page: Int,
        @Query("language") isoCode: String
    ): TvSeriesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") isoCode: String
    ): MovieDetailsResponse

    @GET("tv/{tv_id}")
    suspend fun getTvSeriesDetails(
        @Path("tv_id") tvSeriesId: Int,
        @Query("language") isoCode: String
    ): TvSeriesDetailsResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("language") isoCode: String
    ): CreditsDto

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int,
        @Query("language") isoCode: String
    ): MoviesResponse

    @GET("tv/{tv_id}/similar")
    suspend fun getSimilarTvSeries(
        @Path("tv_id") tvSeriesId: Int,
        @Query("page") page: Int,
        @Query("language") isoCode: String
    ): TvSeriesResponse

    @GET("movie/{movie_id}/recommendations")
    suspend fun getMoviesRecommendations(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int,
        @Query("language") isoCode: String
    ): MoviesResponse

    @GET("tv/{tv_id}/recommendations")
    suspend fun getTvSeriesRecommendations(
        @Path("tv_id") tvSeriesId: Int,
        @Query("page") page: Int,
        @Query("language") isoCode: String
    ): TvSeriesResponse


/*    @GET("search/multi")
    suspend fun multiSearch(
        @Query("page") page: Int,
        @Query("language") isoCode: String,
        @Query("query") query: String,
        @Query("year") year: Int?,
        @Query("include_adult") includeAdult: Boolean,
        @Query("primary_release_year") releaseYear: Int?
    ): SearchResponse*/

    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("page") page: Int,
        @Query("language") isoCode: String
    ): MoviesResponse

    @GET("trending/tv/week")
    suspend fun getTrendingTvSeries(
        @Query("page") page: Int,
        @Query("language") isoCode: String
    ): TvSeriesResponse

    @GET("genre/movie/list")
    fun getMovieGenres(
        @Query("language") isoCode: String
    ): Call<GenresResponse>

    @GET("genre/tv/list")
    suspend fun getTvSeriesGenres(
        @Query("language") isoCode: String
    ): GenresResponse
}