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
package com.kanyideveloper.muviz.home.presentation

import androidx.paging.PagingData
import com.kanyideveloper.muviz.genre.domain.model.Genre
import com.kanyideveloper.muviz.home.domain.model.Movie
import com.kanyideveloper.muviz.home.domain.model.Series
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeUiState(
    // Movies
    val trendingMovies: Flow<PagingData<Movie>> = emptyFlow(),
    val upcomingMovies: Flow<PagingData<Movie>> = emptyFlow(),
    val topRatedMovies: Flow<PagingData<Movie>> = emptyFlow(),
    val nowPlayingMovies: Flow<PagingData<Movie>> = emptyFlow(),
    val popularMovies: Flow<PagingData<Movie>> = emptyFlow(),

    // Tv Series
    val trendingTvSeries: Flow<PagingData<Series>> = emptyFlow(),
    val onAirTvSeries: Flow<PagingData<Series>> = emptyFlow(),
    val topRatedTvSeries: Flow<PagingData<Series>> = emptyFlow(),
    val airingTodayTvSeries: Flow<PagingData<Series>> = emptyFlow(),
    val popularTvSeries: Flow<PagingData<Series>> = emptyFlow(),

    val selectedFilmOption: String = "Movies",
    val tvSeriesGenres: List<Genre> = emptyList(),
    val moviesGenres: List<Genre> = emptyList(),
    val selectedGenre: Genre? = null,
)
