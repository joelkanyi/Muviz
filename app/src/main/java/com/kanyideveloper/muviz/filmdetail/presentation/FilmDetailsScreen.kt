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
package com.kanyideveloper.muviz.filmdetail.presentation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kanyideveloper.muviz.common.domain.model.Film
import com.kanyideveloper.muviz.common.util.Constants.TYPE_MOVIE
import com.kanyideveloper.muviz.common.util.Constants.TYPE_TV_SERIES
import com.kanyideveloper.muviz.filmdetail.presentation.common.CastDetails
import com.kanyideveloper.muviz.filmdetail.presentation.common.FilmActions
import com.kanyideveloper.muviz.filmdetail.presentation.common.FilmImageBanner
import com.kanyideveloper.muviz.filmdetail.presentation.common.FilmInfo
import com.kanyideveloper.muviz.filmdetail.presentation.common.FilmNameAndRating
import com.kanyideveloper.muviz.filmdetail.presentation.common.Genres
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.CastDetailsScreenDestination
import com.ramcosta.composedestinations.generated.destinations.CastsScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalSharedTransitionApi::class)
@Destination<RootGraph>
@Composable
fun SharedTransitionScope.FilmDetailsScreen(
    film: Film,
    navigator: DestinationsNavigator,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: FilmDetailsViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getFilmDetails(
            filmId = film.id,
            filmType = film.type
        )
    }
    val isFilmFavorite = viewModel.isAFavorite(film.id).observeAsState().value != null
    val filmDetailsUiState by viewModel.filmDetailsUiState.collectAsState()

    FilmDetailsScreenContent(
        film = film,
        isLiked = isFilmFavorite,
        state = filmDetailsUiState,
        animatedVisibilityScope = animatedVisibilityScope,
        onEvents = { event ->
            when (event) {
                is FilmDetailsUiEvents.NavigateBack -> {
                    navigator.popBackStack()
                }

                is FilmDetailsUiEvents.NavigateToCastsScreen -> {
                    navigator.navigate(
                        CastsScreenDestination(event.credits)
                    )
                }

                is FilmDetailsUiEvents.AddToFavorites -> {
                    viewModel.insertFavorite(
                        event.favorite
                    )
                }

                is FilmDetailsUiEvents.RemoveFromFavorites -> {
                    viewModel.deleteFavorite(
                        event.favorite
                    )
                }

                is FilmDetailsUiEvents.NavigateToCastDetails -> {
                    navigator.navigate(CastDetailsScreenDestination(event.cast))
                }
            }
        }
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.FilmDetailsScreenContent(
    film: Film,
    state: FilmDetailsUiState,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onEvents: (FilmDetailsUiEvents) -> Unit,
    isLiked: Boolean,
) {
    Scaffold(
        modifier = Modifier.sharedBounds(
            sharedContentState = rememberSharedContentState(key = "${film.id}_${film.category}"),
            animatedVisibilityScope = animatedVisibilityScope,
        ),
        topBar = {
            FilmActions(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                onEvents = onEvents,
                isLiked = isLiked,
                film = film,
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                FilmImageBanner(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(450.dp),
                    filmImage = film.image,
                )
            }

            item {
                FilmNameAndRating(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    filmName = film.name,
                    rating = film.rating,
                )
            }


            item {
                FilmInfo(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    filmOverview = film.overview,
                    filmReleaseDate = film.releaseDate,
                )
            }


            if (state.isLoading.not() &&
                ((film.type == TYPE_TV_SERIES && state.tvSeriesDetails != null) || (film.type == TYPE_MOVIE && state.movieDetails != null)) &&
                state.error == null
            ) {
                item {
                    Genres(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        filmType = film.type,
                        state = state
                    )
                }
            }

            item {
                CastDetails(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    state = state,
                    onEvent = onEvents,
                )
            }

            if (state.isLoading) {
                item {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            if (
                state.isLoading.not() &&
                state.error != null
            ) {
                item {
                    Text(
                        text = state.error,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
fun SharedTransitionScope.FilmDetailsScreenPreview() {
    FilmDetailsScreenContent(
        film = Film(
            id = 1,
            type = TYPE_MOVIE,
            image = "https://image.tmdb.org/t/p/w500/6MKr3KgOLmzOP6MSuZERO41Lpkt.jpg",
            category = "Action",
            name = "The Tomorrow War",
            rating = 7.5f,
            releaseDate = "2021-07-02",
            overview = "The world is stunned when a group of time travelers arrive from the year 2051 to deliver an urgent message: Thirty years in the future, mankind is losing a global war against a deadly alien species. The only hope for survival is for soldiers and civilians from the present to be transported to the future and join the fight. Among those recruited is high school",
        ),
        state = FilmDetailsUiState(),
        onEvents = {},
        isLiked = false,
        animatedVisibilityScope = this as AnimatedVisibilityScope
    )
}
