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
package com.kanyideveloper.muviz.favorites.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanyideveloper.muviz.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.kanyideveloper.muviz.common.presentation.components.StandardToolbar
import com.kanyideveloper.muviz.common.presentation.theme.Transparent
import com.kanyideveloper.muviz.common.presentation.theme.primaryDark
import com.kanyideveloper.muviz.common.presentation.theme.primaryPink
import com.kanyideveloper.muviz.favorites.data.data.local.Favorite
import com.kanyideveloper.muviz.filmdetail.presentation.common.VoteAverageRatingIndicator
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class)
@Destination
@Composable
fun FavoritesScreen(
    navigator: DestinationsNavigator,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        val openDialog = remember { mutableStateOf(false) }
        val favoriteFilms = viewModel.favorites.observeAsState(initial = emptyList())

        StandardToolbar(
            onBackArrowClicked = {
                navigator.popBackStack()
            },
            title = {
                Text(
                    text = "Favorites",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = false,
            navActions = {
                IconButton(onClick = {
                    openDialog.value = true
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        )

        LazyColumn {
            items(
                items = favoriteFilms.value,
                key = { favoriteFilm: Favorite ->
                    favoriteFilm.mediaId
                }
            ) { favorite ->

                val dismissState = rememberDismissState()

                if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                    viewModel.deleteOneFavorite(favorite)
                }
                SwipeToDismiss(
                    state = dismissState,
                    modifier = Modifier
                        .padding(vertical = Dp(1f)),
                    directions = setOf(
                        DismissDirection.EndToStart
                    ),
                    dismissThresholds = { direction ->
                        FractionalThreshold(if (direction == DismissDirection.EndToStart) 0.1f else 0.05f)
                    },
                    background = {
                        val color by animateColorAsState(
                            when (dismissState.targetValue) {
                                DismissValue.Default -> primaryDark
                                else -> primaryPink
                            }
                        )
                        val alignment = Alignment.CenterEnd
                        val icon = Icons.Default.Delete

                        val scale by animateFloatAsState(
                            if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                        )

                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = Dp(20f)),
                            contentAlignment = alignment
                        ) {
                            Icon(
                                icon,
                                contentDescription = "Delete Icon",
                                modifier = Modifier.scale(scale)
                            )
                        }
                    },
                    dismissContent = {

                        Card(
                            elevation = animateDpAsState(
                                if (dismissState.dismissDirection != null) 4.dp else 0.dp
                            ).value,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(230.dp)
                                .align(alignment = Alignment.CenterVertically)
                                .clickable {
                                    if (favorite.mediaType == "tv") {
                                        navigator.navigate(
                                            com.kanyideveloper.muviz.destinations.FilmDetailsScreenDestination(
                                                filmId = favorite.mediaId,
                                                filmType = "tv"
                                            )
                                        )
                                    } else if (favorite.mediaType == "movie") {
                                        navigator.navigate(
                                            com.kanyideveloper.muviz.destinations.FilmDetailsScreenDestination(
                                                filmId = favorite.mediaId,
                                                filmType = "movie"
                                            )
                                        )
                                    }
                                }
                        ) {
                            FilmItem(
                                filmItem = favorite,
                            )
                        }
                    }
                )
            }
        }


        Timber.d(favoriteFilms.value.isEmpty().toString())

        if ((favoriteFilms.value.isEmpty() || favoriteFilms.value.isNullOrEmpty())) {
            Timber.d("Empty")
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .size(250.dp),
                    painter = painterResource(id = R.drawable.ic_empty_cuate),
                    contentDescription = null
                )
            }
        }


        if (openDialog.value) {
            AlertDialog(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(text = "Delete all favorites")
                },
                text = {
                    Text(text = "Are you want to delete all?")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.deleteAllFavorites()
                            openDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(primaryPink)
                    ) {
                        Text(text = "Yes", color = Color.White)
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            openDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(primaryPink)
                    ) {
                        Text(text = "No", color = Color.White)
                    }
                },
                backgroundColor = Color.White,
                contentColor = Color.Black,
                shape = RoundedCornerShape(10.dp)
            )
        }

    }
}

@Composable
fun FilmItem(
    filmItem: Favorite,
) {
    Box {
        Image(
            painter = rememberImagePainter(
                data = filmItem.image,
                builder = {
                    placeholder(R.drawable.ic_placeholder)
                    crossfade(true)
                }
            ),
            modifier = Modifier.fillMaxSize(),
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

        FilmDetails(
            title = filmItem.title,
            releaseDate = filmItem.releaseDate,
            rating = filmItem.rating
        )
    }
}

@Composable
fun FilmDetails(
    title: String,
    releaseDate: String,
    rating: Float
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = releaseDate,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                )
            }
            VoteAverageRatingIndicator(
                percentage = rating
            )
        }
    }
}
