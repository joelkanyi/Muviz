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

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.kanyideveloper.muviz.R
import com.kanyideveloper.muviz.common.presentation.theme.AppBarCollapsedHeight
import com.kanyideveloper.muviz.common.presentation.theme.AppBarExpendedHeight
import com.kanyideveloper.muviz.common.presentation.theme.Transparent
import com.kanyideveloper.muviz.common.presentation.theme.primaryDark
import com.kanyideveloper.muviz.common.util.Constants
import com.kanyideveloper.muviz.favorites.data.data.local.Favorite
import com.kanyideveloper.muviz.filmdetail.presentation.FilmDetailsUiEvents
import com.kanyideveloper.muviz.filmdetail.presentation.FilmDetailsUiState
import kotlin.math.max
import kotlin.math.min

@Composable
fun FilmImageBanner(
    scrollState: LazyListState,
    state: FilmDetailsUiState,
    onEvents: (FilmDetailsUiEvents) -> Unit,
    isLiked: Boolean,
    filmType: String,
) {

    val context = LocalContext.current
    val filmName = if (filmType == "movie") state.movieDetails?.title.toString() else state.tvSeriesDetails?.name.toString()
    val posterUrl = "${Constants.IMAGE_BASE_UR}/${
        if (filmType == "movie") state.movieDetails?.posterPath else state.tvSeriesDetails?.posterPath
    }"
    val rating = if (filmType == "movie") state.movieDetails?.voteAverage?.toFloat()!! else state.tvSeriesDetails?.voteAverage?.toFloat()!!
    val releaseDate = if (filmType == "movie") state.movieDetails?.releaseDate.toString() else state.tvSeriesDetails?.firstAirDate.toString()
    val filmId = if (filmType == "movie") state.movieDetails?.id!! else state.tvSeriesDetails?.id!!

    val imageHeight = AppBarExpendedHeight - AppBarCollapsedHeight

    val maxOffset = with(LocalDensity.current) {
        imageHeight.roundToPx()
    } - LocalWindowInsets.current.systemBars.layoutInsets.top

    val offset = min(scrollState.firstVisibleItemScrollOffset, maxOffset)

    val offsetProgress = max(0f, offset * 3f - 2f * maxOffset) / maxOffset

    TopAppBar(
        contentPadding = PaddingValues(),
        backgroundColor = primaryDark,
        modifier = Modifier
            .height(
                AppBarExpendedHeight
            )
            .offset { IntOffset(x = 0, y = -offset) },
        elevation = if (offset == maxOffset) 4.dp else 0.dp
    ) {
        Column {
            Box {
                Image(
                    painter = rememberImagePainter(
                        data = posterUrl,
                        builder = {
                            placeholder(R.drawable.ic_placeholder)
                            crossfade(true)
                        }
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .height(imageHeight)
                        .graphicsLayer {
                            alpha = 1f - offsetProgress
                        },
                    contentScale = ContentScale.Crop,
                    contentDescription = "Movie Banner"
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colorStops = arrayOf(
                                    Pair(0.3f, Transparent),
                                    Pair(1.5f, primaryDark)
                                )
                            )
                        )
                )
                FilmNameAndRating(
                    filmName = filmName,
                    rating = rating
                )
            }
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 10.dp)
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
                    onEvents(FilmDetailsUiEvents.RemoveFromFavorites(
                        Favorite(
                            favorite = false,
                            mediaId = filmId,
                            mediaType = filmType,
                            image = posterUrl,
                            title = filmName,
                            releaseDate = releaseDate,
                            rating = rating
                        )
                    ))
                } else {
                    onEvents(FilmDetailsUiEvents.AddToFavorites(
                        Favorite(
                            favorite = true,
                            mediaId = filmId,
                            mediaType = filmType,
                            image = posterUrl,
                            title = filmName,
                            releaseDate = releaseDate,
                            rating = rating
                        )
                    ))
                }
            }
        )
    }
}
