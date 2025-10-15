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
import io.github.joelkanyi.muviz.mobile.home.domain.model.Series
import io.github.joelkanyi.muviz.mobile.home.data.paging.AiringTodayTvSeriesSource
import io.github.joelkanyi.muviz.mobile.home.data.paging.OnTheAirSeriesSource
import io.github.joelkanyi.muviz.mobile.home.data.paging.PopularSeriesSource
import io.github.joelkanyi.muviz.mobile.home.data.paging.TopRatedSeriesSource
import io.github.joelkanyi.muviz.mobile.home.data.paging.TrendingSeriesSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvSeriesRepository @Inject constructor(private val api: TMDBApi) {
    fun getTrendingThisWeekTvSeries(): Flow<PagingData<Series>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGING_SIZE),
            pagingSourceFactory = {
                TrendingSeriesSource(
                    api
                )
            }
        ).flow
    }

    fun getOnTheAirTvSeries(): Flow<PagingData<Series>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGING_SIZE),
            pagingSourceFactory = {
                OnTheAirSeriesSource(
                    api
                )
            }
        ).flow
    }

    fun getTopRatedTvSeries(): Flow<PagingData<Series>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGING_SIZE),
            pagingSourceFactory = {
                TopRatedSeriesSource(
                    api
                )
            }
        ).flow
    }

    fun getAiringTodayTvSeries(): Flow<PagingData<Series>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGING_SIZE),
            pagingSourceFactory = {
                AiringTodayTvSeriesSource(
                    api
                )
            }
        ).flow
    }

    fun getPopularTvSeries(): Flow<PagingData<Series>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGING_SIZE),
            pagingSourceFactory = {
                PopularSeriesSource(
                    api
                )
            }
        ).flow
    }
}
