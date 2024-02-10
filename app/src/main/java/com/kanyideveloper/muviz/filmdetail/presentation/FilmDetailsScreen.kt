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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.kanyideveloper.muviz.destinations.CastsScreenDestination
import com.kanyideveloper.muviz.filmdetail.presentation.common.FilmImageBanner
import com.kanyideveloper.muviz.filmdetail.presentation.common.FilmInfo
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun FilmDetailsScreen(
    filmId: Int,
    filmType: String,
    navigator: DestinationsNavigator,
    viewModel: FilmDetailsViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getFilmDetails(filmId, filmType)
    }
    val isFilmFavorite = viewModel.isAFavorite(filmId).observeAsState().value != null
    val filmDetailsUiState by viewModel.filmDetailsUiState.collectAsState()

    FilmDetailsScreenContent(
        filmType = filmType,
        isLiked = isFilmFavorite,
        state = filmDetailsUiState,
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
            }
        }
    )
}

@Composable
fun FilmDetailsScreenContent(
    filmType: String,
    state: FilmDetailsUiState,
    onEvents: (FilmDetailsUiEvents) -> Unit,
    isLiked: Boolean,
) {
    val scrollState = rememberLazyListState()

    Box {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = androidx.compose.ui.Modifier
                    .align(androidx.compose.ui.Alignment.Center)
            )
        }

        if (state.isLoading.not() &&
            ((filmType == "tv" && state.tvSeriesDetails != null) || (filmType == "movie" && state.movieDetails != null)) &&
            state.error == null
        ) {
            FilmInfo(
                scrollState = scrollState,
                filmType = filmType,
                state = state,
                onEvent = onEvents,
            )
            FilmImageBanner(
                scrollState = scrollState,
                filmType = filmType,
                state = state,
                isLiked = isLiked,
                onEvents = onEvents,
            )
        }

        if (
            state.isLoading.not() &&
            state.error != null
        ) {
            Text(
                text = state.error,
                modifier = androidx.compose.ui.Modifier
                    .align(androidx.compose.ui.Alignment.Center)
            )
        }
    }
}

@Preview
@Composable
fun FilmDetailsScreenPreview() {
    FilmDetailsScreenContent(
        filmType = "tv",
        state = FilmDetailsUiState(),
        onEvents = {},
        isLiked = false,
    )
}
