package com.kanyideveloper.muviz.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kanyideveloper.muviz.data.remote.TMDBApi
import com.kanyideveloper.muviz.model.Movie
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class TrendingMoviesSource(private val api: TMDBApi) :
    PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val trendingMoviesList = api.getTrendingTodayMovies(nextPage)
            Timber.d("trending movies list : ${trendingMoviesList.searches}")
            LoadResult.Page(
                data = trendingMoviesList.searches,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (trendingMoviesList.searches.isEmpty()) null else trendingMoviesList.page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}