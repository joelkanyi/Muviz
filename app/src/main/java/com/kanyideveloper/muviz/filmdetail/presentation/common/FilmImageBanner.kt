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
package com.kanyideveloper.muviz.filmdetail.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kanyideveloper.muviz.R
import com.kanyideveloper.muviz.common.presentation.theme.AppBarCollapsedHeight
import com.kanyideveloper.muviz.common.presentation.theme.AppBarExpendedHeight
import com.kanyideveloper.muviz.common.util.Constants
import com.kanyideveloper.muviz.favorites.data.data.local.Favorite
import com.kanyideveloper.muviz.filmdetail.presentation.FilmDetailsUiEvents
import com.kanyideveloper.muviz.filmdetail.presentation.FilmDetailsUiState

@Composable
fun FilmImageBanner(
    modifier: Modifier = Modifier,
    state: FilmDetailsUiState,
    onEvents: (FilmDetailsUiEvents) -> Unit,
    isLiked: Boolean,
    filmType: String,
) {

    val filmName =
        if (filmType == "movie") state.movieDetails?.title.toString() else state.tvSeriesDetails?.name.toString()
    val posterUrl = "${Constants.IMAGE_BASE_UR}/${
        if (filmType == "movie") state.movieDetails?.posterPath else state.tvSeriesDetails?.posterPath
    }"
    val rating =
        if (filmType == "movie") state.movieDetails?.voteAverage?.toFloat()!! else state.tvSeriesDetails?.voteAverage?.toFloat()!!
    val releaseDate =
        if (filmType == "movie") state.movieDetails?.releaseDate.toString() else state.tvSeriesDetails?.firstAirDate.toString()
    val filmId = if (filmType == "movie") state.movieDetails?.id!! else state.tvSeriesDetails?.id!!

    val imageHeight = AppBarExpendedHeight - AppBarCollapsedHeight

    Box(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(posterUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_placeholder),
            contentDescription = "Movie Banner",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .height(imageHeight),
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            Pair(0.3f, Transparent),
                            Pair(1.5f, MaterialTheme.colorScheme.background)
                        )
                    )
                )
        )
        FilmNameAndRating(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            filmName = filmName,
            rating = rating
        )


        FilmActions(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .align(Alignment.TopStart),
            onEvents = onEvents,
            isLiked = isLiked,
            filmId = filmId,
            filmType = filmType,
            posterUrl = posterUrl,
            filmName = filmName,
            releaseDate = releaseDate,
            rating = rating
        )
    }
}

@Composable
private fun FilmActions(
    modifier: Modifier = Modifier,
    onEvents: (FilmDetailsUiEvents) -> Unit,
    isLiked: Boolean,
    filmId: Int,
    filmType: String,
    posterUrl: String,
    filmName: String,
    releaseDate: String,
    rating: Float
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        CircularBackButtons(
            onClick = {
                onEvents(FilmDetailsUiEvents.NavigateBack)
            }
        )
        CircularFavoriteButtons(
            isLiked = isLiked,
            onClick = { isFav ->
                if (isFav) {
                    onEvents(
                        FilmDetailsUiEvents.RemoveFromFavorites(
                            Favorite(
                                favorite = false,
                                mediaId = filmId,
                                mediaType = filmType,
                                image = posterUrl,
                                title = filmName,
                                releaseDate = releaseDate,
                                rating = rating
                            )
                        )
                    )
                } else {
                    onEvents(
                        FilmDetailsUiEvents.AddToFavorites(
                            Favorite(
                                favorite = true,
                                mediaId = filmId,
                                mediaType = filmType,
                                image = posterUrl,
                                title = filmName,
                                releaseDate = releaseDate,
                                rating = rating
                            )
                        )
                    )
                }
            }
        )
    }
}
