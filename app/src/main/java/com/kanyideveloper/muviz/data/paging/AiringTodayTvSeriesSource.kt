package com.kanyideveloper.muviz.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kanyideveloper.muviz.data.remote.TMDBApi
import com.kanyideveloper.muviz.data.remote.responses.Movie
import com.kanyideveloper.muviz.data.remote.responses.Series
import retrofit2.HttpException
import java.io.IOException

class AiringTodayTvSeriesSource(private val api: TMDBApi) :
    PagingSource<Int, Series>() {
    override fun getRefreshKey(state: PagingState<Int, Series>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Series> {
        return try {
            val nextPage = params.key ?: 1
            val trendingMoviesList = api.getAiringTodayTvSeries(nextPage)
            LoadResult.Page(
                data = trendingMoviesList.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (trendingMoviesList.results.isEmpty()) null else trendingMoviesList.page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}