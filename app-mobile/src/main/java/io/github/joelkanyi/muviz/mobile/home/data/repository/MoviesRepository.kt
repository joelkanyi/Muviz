/*
 * Copyright 2024 Joel Kanyi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.joelkanyi.muviz.mobile.home.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.joelkanyi.muviz.mobile.common.data.network.TMDBApi
import io.github.joelkanyi.core.common.util.Constants.PAGING_SIZE
import io.github.joelkanyi.muviz.mobile.home.domain.model.Movie
import io.github.joelkanyi.muviz.mobile.home.data.paging.NowPlayingMoviesSource
import io.github.joelkanyi.muviz.mobile.home.data.paging.PopularMoviesSource
import io.github.joelkanyi.muviz.mobile.home.data.paging.TopRatedMoviesSource
import io.github.joelkanyi.muviz.mobile.home.data.paging.TrendingMoviesSource
import io.github.joelkanyi.muviz.mobile.home.data.paging.UpcomingMoviesSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val api: TMDBApi) {

    fun getTrendingMoviesThisWeek(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGING_SIZE),
            pagingSourceFactory = {
                TrendingMoviesSource(
                    api
                )
            }
        ).flow
    }

    fun getUpcomingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGING_SIZE),
            pagingSourceFactory = {
                UpcomingMoviesSource(
                    api
                )
            }
        ).flow
    }

    fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGING_SIZE),
            pagingSourceFactory = {
                TopRatedMoviesSource(
                    api
                )
            }
        ).flow
    }

    fun getNowPlayingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGING_SIZE),
            pagingSourceFactory = {
                NowPlayingMoviesSource(
                    api
                )
            }
        ).flow
    }

    fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGING_SIZE),
            pagingSourceFactory = {
                PopularMoviesSource(
                    api
                )
            }
        ).flow
    }
}
