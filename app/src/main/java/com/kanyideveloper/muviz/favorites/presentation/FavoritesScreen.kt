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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.kanyideveloper.muviz.R
import com.kanyideveloper.muviz.common.domain.model.Film
import com.kanyideveloper.muviz.common.presentation.components.StandardToolbar
import com.kanyideveloper.muviz.common.presentation.theme.MuvizTheme
import com.kanyideveloper.muviz.common.util.Constants.TYPE_MOVIE
import com.kanyideveloper.muviz.common.util.Constants.TYPE_TV_SERIES
import com.kanyideveloper.muviz.favorites.data.data.local.Favorite
import com.kanyideveloper.muviz.filmdetail.presentation.common.VoteAverageRatingIndicator
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.FilmDetailsScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay

@Destination<RootGraph>
@Composable
fun FavoritesScreen(
    navigator: DestinationsNavigator,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    var openDialog by rememberSaveable { mutableStateOf(false) }
    val favoriteFilms by viewModel.favorites.collectAsState()

    FavoritesScreenContent(
        favoriteFilms = favoriteFilms,
        showDeleteConsentDialog = openDialog,
        onNavigateBack = {
            navigator.popBackStack()
        },
        onClickDeleteAllFavorites = {
            openDialog = true
        },
        onDismissDeleteConsentDialog = {
            openDialog = false
        },
        onDeleteOneFavorite = { favorite ->
            viewModel.deleteOneFavorite(favorite)
        },
        onClickAFavorite = { favorite ->
            if (favorite.mediaType == TYPE_TV_SERIES) {
                navigator.navigate(
                    FilmDetailsScreenDestination(
                        film = favorite.toFilm()
                    )
                )
            } else if (favorite.mediaType == TYPE_MOVIE) {
                navigator.navigate(
                    FilmDetailsScreenDestination(
                        film = favorite.toFilm()
                    )
                )
            }
        },
        onConfirmDeleteAllFavorites = {
            viewModel.deleteAllFavorites()
            openDialog = false
        }
    )
}

@Composable
private fun FavoritesScreenContent(
    favoriteFilms: List<Favorite>,
    showDeleteConsentDialog: Boolean,
    onDismissDeleteConsentDialog: () -> Unit,
    onConfirmDeleteAllFavorites: () -> Unit,
    onNavigateBack: () -> Unit,
    onClickDeleteAllFavorites: () -> Unit,
    onDeleteOneFavorite: (Favorite) -> Unit,
    onClickAFavorite: (Favorite) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            StandardToolbar(
                onBackArrowClicked = onNavigateBack,
                title = {
                    Text(
                        text = stringResource(R.string.favorites),
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                showBackArrow = false,
                navActions = {
                    IconButton(
                        onClick = onClickDeleteAllFavorites
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                        )
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = favoriteFilms,
                key = { favoriteFilm: Favorite ->
                    favoriteFilm.mediaId
                }
            ) { favorite ->
                SwipeToDeleteContainer(
                    item = favorite,
                    onDelete = onDeleteOneFavorite,
                    content = {
                        FilmItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(230.dp),
                            filmItem = favorite,
                            onClick = {
                                onClickAFavorite(favorite)
                            }
                        )
                    }
                )
            }
        }


        if (favoriteFilms.isEmpty()) {
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


        if (showDeleteConsentDialog) {
            AlertDialog(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                tonalElevation = 0.dp,
                onDismissRequest = {
                    onDismissDeleteConsentDialog()
                },
                title = {
                    Text(text = stringResource(R.string.delete_all_favorites))
                },
                text = {
                    Text(text = stringResource(R.string.are_you_want_to_delete_all))
                },
                confirmButton = {
                    Button(
                        onClick = onConfirmDeleteAllFavorites,
                    ) {
                        Text(text = stringResource(R.string.yes))
                    }
                },
                dismissButton = {
                    Button(
                        onClick = onDismissDeleteConsentDialog,
                    ) {
                        Text(text = stringResource(R.string.no))
                    }
                },
                shape = RoundedCornerShape(10.dp)
            )
        }
    }
}

@Composable
fun FilmItem(
    filmItem: Favorite,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        onClick = onClick
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
                                Pair(
                                    1.5f, MaterialTheme.colorScheme.background
                                )
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
}

@Composable
fun FilmDetails(
    modifier: Modifier = Modifier,
    title: String,
    releaseDate: String,
    rating: Float
) {
    Row(
        modifier = modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.Bottom
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = releaseDate,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Light,
                    ),
                )
            }
            VoteAverageRatingIndicator(
                percentage = rating
            )
        }
    }
}


@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                isRemoved = true
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = state,
            backgroundContent = {
                DeleteBackground(swipeDismissState = state)
            },
            content = { content(item) },
            enableDismissFromStartToEnd = false
        )
    }
}

@Composable
fun DeleteBackground(
    swipeDismissState: SwipeToDismissBoxState
) {
    val color by animateColorAsState(
        when (swipeDismissState.targetValue) {
            SwipeToDismissBoxValue.Settled -> MaterialTheme.colorScheme.background
            else -> MaterialTheme.colorScheme.primary
        }, label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}


fun Favorite.toFilm() = Film(
    id = mediaId,
    type = mediaType,
    image = image,
    category = "",
    name = title,
    rating = rating,
    releaseDate = releaseDate,
    overview = overview
)

@Preview
@Composable
private fun FavoritesScreenPreview() {
    MuvizTheme {
        FavoritesScreenContent(
            favoriteFilms = listOf(
                Favorite(
                    favorite = true,
                    mediaId = 1,
                    mediaType = TYPE_MOVIE,
                    image = "https://example.com/image.jpg",
                    title = "Example Movie Very Long Title That Exceeds Normal Length",
                    releaseDate = "2023-01-01",
                    rating = 48.5f,
                    overview = "This is an example movie overview."
                )
            ),
            showDeleteConsentDialog = false,
            onDismissDeleteConsentDialog = {},
            onConfirmDeleteAllFavorites = {},
            onNavigateBack = {},
            onClickDeleteAllFavorites = {},
            onDeleteOneFavorite = {},
            onClickAFavorite = {}
        )
    }
}
