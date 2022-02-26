package com.kanyideveloper.muviz.screens.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kanyideveloper.muviz.R
import com.kanyideveloper.muviz.screens.commons.MovieItem
import com.kanyideveloper.muviz.presentation.components.StandardToolbar
import com.kanyideveloper.muviz.screens.destinations.DetailsScreenDestination
import com.kanyideveloper.muviz.screens.destinations.SearchScreenDestination
import com.kanyideveloper.muviz.ui.theme.lightGray
import com.kanyideveloper.muviz.ui.theme.primaryDark
import com.kanyideveloper.muviz.ui.theme.primaryGray
import com.kanyideveloper.muviz.ui.theme.primaryPink
import com.kanyideveloper.muviz.util.Constants.IMAGE_BASE_UR
import com.kanyideveloper.muviz.util.Constants.IMAGE_BASE_URL
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {

    val popularMovies = viewModel.popularMovies
    val popularTvSeries = viewModel.popularTvSeries

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        StandardToolbar(
            navigator = navigator,
            title = {
                Column {
                    Image(
                        painterResource(
                            id = R.drawable.muviz
                        ),
                        contentDescription = "App logo",
                        modifier = Modifier
                            .size(width = 90.dp, height = 90.dp)
                            .padding(8.dp)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = false,
            navActions = {
                IconButton(onClick = {
                    navigator.navigate(SearchScreenDestination)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null,
                        tint = primaryGray
                    )
                }
            }
        )

        LazyColumn {
            item {
                FilmType(
                    items = listOf("Movies", "Tv Shows"),
                    modifier = Modifier.fillMaxWidth(),
                    viewModel = viewModel
                )
                Spacer(modifier = Modifier.height(5.dp))

            }
            item {
                Text(
                    text = "Genres",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )

                Spacer(modifier = Modifier.height(5.dp))

            }
            item {
                Genres(
                    viewModel = viewModel
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Latest", color = Color.White, fontSize = 18.sp)
            }
            item {
                Spacer(modifier = Modifier.height(5.dp))
                LazyRow(content = {

                    if (viewModel.selectedOption.value == "Tv Shows") {
                        items(popularTvSeries.value) { film ->

                            MovieItem(
                                cardModifier = Modifier
                                    .height(210.dp)
                                    .width(240.dp)
                                    .clickable {
                                        navigator.navigate(DetailsScreenDestination)
                                    },
                                imageUrl = "$IMAGE_BASE_UR/${film.poster_path}"
                            )
                        }
                    } else {
                        items(popularMovies.value) { film ->

                            MovieItem(
                                cardModifier = Modifier
                                    .height(200.dp)
                                    .width(230.dp)
                                    .clickable {
                                        navigator.navigate(DetailsScreenDestination)
                                    },
                                imageUrl = "$IMAGE_BASE_UR/${film.posterPath}"
                            )
                        }
                    }
                })
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Top Rated", color = Color.White, fontSize = 18.sp)
            }
            item {
                Spacer(modifier = Modifier.height(5.dp))
                LazyRow(content = {
                    items(10) {
                        MovieItem(
                            cardModifier = Modifier
                                .height(200.dp)
                                .width(130.dp),
                            imageUrl = IMAGE_BASE_URL
                        )
                    }
                })
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Upcoming", color = Color.White, fontSize = 18.sp)
            }
            item {
                Spacer(modifier = Modifier.height(5.dp))
                LazyRow(content = {
                    items(10) {
                        MovieItem(
                            cardModifier = Modifier
                                .height(200.dp)
                                .width(130.dp),
                            imageUrl = IMAGE_BASE_URL
                        )
                    }
                })
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Now Playing", color = Color.White, fontSize = 18.sp)
            }
            item {
                Spacer(modifier = Modifier.height(5.dp))
                LazyRow(content = {
                    items(10) {
                        MovieItem(
                            cardModifier = Modifier
                                .height(200.dp)
                                .width(130.dp),
                            imageUrl = IMAGE_BASE_URL
                        )
                    }
                })

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun FilmType(
    items: List<String>,
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {

        items.forEach { item ->

            val lineLength = animateFloatAsState(
                targetValue = if (item == viewModel.selectedOption.value) 2f else 0f,
                animationSpec = tween(
                    durationMillis = 300
                )
            )

            Text(
                text = item,
                color = if (item == viewModel.selectedOption.value) Color.White else primaryGray,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(8.dp)
                    .drawBehind {
                        if (item == viewModel.selectedOption.value) {
                            if (lineLength.value > 0f) {
                                drawLine(
                                    color = if (item == viewModel.selectedOption.value) {
                                        primaryPink
                                    } else {
                                        lightGray
                                    },
                                    start = Offset(
                                        size.width / 2f - lineLength.value * 10.dp.toPx(),
                                        size.height
                                    ),
                                    end = Offset(
                                        size.width / 2f + lineLength.value * 10.dp.toPx(),
                                        size.height
                                    ),
                                    strokeWidth = 2.dp.toPx(),
                                    cap = StrokeCap.Round
                                )
                            }
                        }
                    }
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        viewModel.setSelectedOption(item)
                    }
            )
        }
    }
}

@Composable
fun Genres(
    viewModel: HomeScreenViewModel
) {
    val genres = if (viewModel.selectedOption.value == "Tv Shows") {
        viewModel.tvSeriesGenres.value
    } else {
        viewModel.moviesGenres.value
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        items(items = genres) { genre ->
            Text(
                text = genre.name,
                style = typography.body1.merge(),
                color = Color.White,
                modifier = Modifier
                    .clip(
                        shape = RoundedCornerShape(
                            size = 8.dp,
                        ),
                    )
                    .clickable {
                        viewModel.setGenre(genre.name)
                        viewModel.getPopularMovies(genre.id)
                    }
                    .background(
                        if (genre.name == viewModel.selectedGenre.value) {
                            primaryPink
                        } else {
                            primaryDark
                        }
                    )
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 10.dp,
                        bottom = 10.dp
                    )
            )
        }
    }
}