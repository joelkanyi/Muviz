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

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kanyideveloper.muviz.R
import com.kanyideveloper.muviz.common.domain.model.Film
import com.kanyideveloper.muviz.common.presentation.components.StandardToolbar
import com.kanyideveloper.muviz.common.util.Constants.TYPE_MOVIE
import com.kanyideveloper.muviz.common.util.Constants.TYPE_TV_SERIES
import com.kanyideveloper.muviz.common.util.createImageUrl
import com.kanyideveloper.muviz.home.domain.model.Movie
import com.kanyideveloper.muviz.home.domain.model.Series
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.FilmDetailsScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalSharedTransitionApi::class)
@Destination<RootGraph>(start = true)
@Composable
fun SharedTransitionScope.HomeScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    val context = LocalContext.current

    HomeScreenContent(
        state = homeUiState,
        animatedVisibilityScope = animatedVisibilityScope,
        onEvent = { event ->
            when (event) {
                is HomeUiEvents.NavigateBack -> {
                    navigator.navigateUp()
                }

                is HomeUiEvents.NavigateToFilmDetails -> {
                    navigator.navigate(
                        FilmDetailsScreenDestination(
                            film = event.film,
                        )
                    )
                }

                is HomeUiEvents.OnFilmGenreSelected -> {
                    if (homeUiState.selectedFilmOption == context.getString(R.string.movies)) {
                        viewModel.setGenre(event.genre)
                        viewModel.getTrendingMovies(event.genre.id)
                        viewModel.getTopRatedMovies(event.genre.id)
                        viewModel.getUpcomingMovies(event.genre.id)
                        viewModel.getNowPayingMovies(event.genre.id)
                        viewModel.getPopularMovies(event.genre.id)
                    } else if (homeUiState.selectedFilmOption == context.getString(R.string.tv_shows)) {
                        viewModel.setGenre(event.genre)
                        viewModel.getTrendingTvSeries(event.genre.id)
                        viewModel.getTopRatedTvSeries(event.genre.id)
                        viewModel.getAiringTodayTvSeries(event.genre.id)
                        viewModel.getOnTheAirTvSeries(event.genre.id)
                        viewModel.getPopularTvSeries(event.genre.id)
                    }
                }

                is HomeUiEvents.OnFilmOptionSelected -> {
                    viewModel.setSelectedOption(event.item)
                }

                HomeUiEvents.OnPullToRefresh -> {
                    viewModel.refreshAllData()
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.HomeScreenContent(
    state: HomeUiState,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onEvent: (HomeUiEvents) -> Unit,
) {
    val context = LocalContext.current
    val trendingMovies = state.trendingMovies.collectAsLazyPagingItems()
    val upcomingMovies = state.upcomingMovies.collectAsLazyPagingItems()
    val topRatedMovies = state.topRatedMovies.collectAsLazyPagingItems()
    val nowPlayingMovies = state.nowPlayingMovies.collectAsLazyPagingItems()
    val popularMovies = state.popularMovies.collectAsLazyPagingItems()
    val trendingTvSeries = state.trendingTvSeries.collectAsLazyPagingItems()
    val onAirTvSeries = state.onAirTvSeries.collectAsLazyPagingItems()
    val topRatedTvSeries = state.topRatedTvSeries.collectAsLazyPagingItems()
    val airingTodayTvSeries = state.airingTodayTvSeries.collectAsLazyPagingItems()
    val popularTvSeries = state.popularTvSeries.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            StandardToolbar(
                onBackArrowClicked = {
                    onEvent(HomeUiEvents.NavigateBack)
                },
                title = {
                    Column {
                        Image(
                            painterResource(
                                id = R.drawable.muviz
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .size(width = 90.dp, height = 90.dp)
                                .padding(8.dp)
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                showBackArrow = false,
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            isRefreshing = false,
            onRefresh = {
                onEvent(HomeUiEvents.OnPullToRefresh)
            }
        ) {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    FilmCategory(
                        items = listOf(
                            stringResource(R.string.movies),
                            stringResource(R.string.tv_shows)
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        state = state,
                        onEvent = onEvent,
                    )
                }

                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.genres),
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Genres(
                            state = state,
                            onEvent = onEvent,
                        )
                    }
                }

                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = stringResource(R.string.trending_today),
                            style = MaterialTheme.typography.titleMedium,
                        )

                        if (state.selectedFilmOption == stringResource(R.string.tv_shows)) {
                            PagedRow(
                                items = trendingTvSeries,
                                modifier = Modifier.fillMaxWidth(),
                                content = {
                                    FilmItem(
                                        modifier = Modifier
                                            .height(220.dp)
                                            .width(250.dp)
                                            .clickable {
                                                onEvent(
                                                    HomeUiEvents.NavigateToFilmDetails(
                                                        film = it.toFilm(
                                                            category = context.getString(R.string.trending_today)
                                                        )
                                                    )
                                                )
                                            },
                                        imageUrl = it.posterPath.createImageUrl(),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        sharedTransitionKey = "${it.id}_${context.getString(R.string.trending_today)}",
                                    )
                                }
                            )
                        } else {
                            PagedRow(
                                items = trendingMovies,
                                modifier = Modifier.fillMaxWidth(),
                                content = {
                                    FilmItem(
                                        modifier = Modifier
                                            .height(200.dp)
                                            .width(230.dp)
                                            .clickable {
                                                onEvent(
                                                    HomeUiEvents.NavigateToFilmDetails(
                                                        film = it.toFilm(
                                                            category = context.getString(R.string.trending_today)
                                                        )
                                                    )
                                                )
                                            },
                                        imageUrl = it.posterPath.createImageUrl(),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        sharedTransitionKey = "${it.id}_${context.getString(R.string.trending_today)}",
                                    )
                                }
                            )
                        }
                    }
                }

                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = stringResource(R.string.popular),
                            style = MaterialTheme.typography.titleMedium,
                        )
                        if (state.selectedFilmOption == stringResource(R.string.tv_shows)) {
                            PagedRow(
                                items = popularTvSeries,
                                modifier = Modifier.fillMaxWidth(),
                                content = {
                                    FilmItem(
                                        modifier = Modifier
                                            .height(200.dp)
                                            .width(130.dp)
                                            .clickable {
                                                onEvent(
                                                    HomeUiEvents.NavigateToFilmDetails(
                                                        film = it.toFilm(
                                                            category = context.getString(R.string.popular)
                                                        )
                                                    )
                                                )
                                            },
                                        imageUrl = it.posterPath.createImageUrl(),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        sharedTransitionKey = "${it.id}_${context.getString(R.string.popular)}",
                                    )
                                }
                            )
                        } else {
                            PagedRow(
                                items = popularMovies,
                                modifier = Modifier.fillMaxWidth(),
                                content = {
                                    FilmItem(
                                        modifier = Modifier
                                            .height(200.dp)
                                            .width(130.dp)
                                            .clickable {
                                                onEvent(
                                                    HomeUiEvents.NavigateToFilmDetails(
                                                        film = it.toFilm(
                                                            category = context.getString(R.string.popular)
                                                        )
                                                    )
                                                )
                                            },
                                        imageUrl = it.posterPath.createImageUrl(),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        sharedTransitionKey = "${it.id}_${context.getString(R.string.popular)}",
                                    )
                                }
                            )
                        }
                    }
                }


                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = if (state.selectedFilmOption == stringResource(R.string.tv_shows)) {
                                stringResource(R.string.on_air)
                            } else {
                                stringResource(R.string.upcoming)
                            },
                            style = MaterialTheme.typography.titleMedium,
                        )
                        if (state.selectedFilmOption == stringResource(R.string.tv_shows)) {
                            PagedRow(
                                items = onAirTvSeries,
                                modifier = Modifier.fillMaxWidth(),
                                content = {
                                    FilmItem(
                                        modifier = Modifier
                                            .height(200.dp)
                                            .width(130.dp)
                                            .clickable {
                                                onEvent(
                                                    HomeUiEvents.NavigateToFilmDetails(
                                                        film = it.toFilm(
                                                            category = context.getString(R.string.on_air)
                                                        )
                                                    )
                                                )
                                            },
                                        imageUrl = it.posterPath.createImageUrl(),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        sharedTransitionKey = "${it.id}_${context.getString(R.string.on_air)}",
                                    )
                                }
                            )
                        } else {
                            PagedRow(
                                items = upcomingMovies,
                                modifier = Modifier.fillMaxWidth(),
                                content = {
                                    FilmItem(
                                        modifier = Modifier
                                            .height(200.dp)
                                            .width(130.dp)
                                            .clickable {
                                                onEvent(
                                                    HomeUiEvents.NavigateToFilmDetails(
                                                        film = it.toFilm(
                                                            category = context.getString(R.string.upcoming)
                                                        )
                                                    )
                                                )
                                            },
                                        imageUrl = it.posterPath.createImageUrl(),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        sharedTransitionKey = "${it.id}_${context.getString(R.string.upcoming)}",
                                    )
                                }
                            )
                        }
                    }
                }

                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = if (state.selectedFilmOption == stringResource(R.string.tv_shows)) {
                                stringResource(R.string.airing_today)
                            } else {
                                stringResource(R.string.now_playing)
                            },
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                    if (state.selectedFilmOption == stringResource(R.string.tv_shows)) {
                        PagedRow(
                            items = airingTodayTvSeries,
                            modifier = Modifier.fillMaxWidth(),
                            content = {
                                FilmItem(
                                    modifier = Modifier
                                        .height(200.dp)
                                        .width(130.dp)
                                        .clickable {
                                            onEvent(
                                                HomeUiEvents.NavigateToFilmDetails(
                                                    film = it.toFilm(
                                                        category = context.getString(R.string.airing_today)
                                                    )
                                                )
                                            )
                                        },
                                    imageUrl = it.posterPath.createImageUrl(),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    sharedTransitionKey = "${it.id}_${context.getString(R.string.airing_today)}",
                                )
                            }
                        )
                    } else {
                        PagedRow(
                            items = nowPlayingMovies,
                            modifier = Modifier.fillMaxWidth(),
                            content = {
                                FilmItem(
                                    modifier = Modifier
                                        .height(200.dp)
                                        .width(130.dp)
                                        .clickable {
                                            onEvent(
                                                HomeUiEvents.NavigateToFilmDetails(
                                                    film = it.toFilm(
                                                        category = context.getString(R.string.now_playing)
                                                    )
                                                )
                                            )
                                        },
                                    imageUrl = it.posterPath.createImageUrl(),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    sharedTransitionKey = "${it.id}_${context.getString(R.string.now_playing)}",
                                )
                            }
                        )
                    }
                }

                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = stringResource(R.string.top_rated),
                            style = MaterialTheme.typography.titleMedium,
                        )
                        if (state.selectedFilmOption == stringResource(R.string.tv_shows)) {
                            PagedRow(
                                items = topRatedTvSeries,
                                modifier = Modifier.fillMaxWidth(),
                                content = {
                                    FilmItem(
                                        modifier = Modifier
                                            .height(200.dp)
                                            .width(130.dp)
                                            .clickable {
                                                onEvent(
                                                    HomeUiEvents.NavigateToFilmDetails(
                                                        film = it.toFilm(
                                                            category = context.getString(R.string.top_rated)
                                                        )
                                                    )
                                                )
                                            },
                                        imageUrl = it.posterPath.createImageUrl(),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        sharedTransitionKey = "${it.id}_${context.getString(R.string.top_rated)}",
                                    )
                                }
                            )
                        } else {
                            PagedRow(
                                items = topRatedMovies,
                                modifier = Modifier.fillMaxWidth(),
                                content = {
                                    FilmItem(
                                        modifier = Modifier
                                            .height(200.dp)
                                            .width(130.dp)
                                            .clickable {
                                                onEvent(
                                                    HomeUiEvents.NavigateToFilmDetails(
                                                        film = it.toFilm(
                                                            category = context.getString(R.string.top_rated)
                                                        )
                                                    )
                                                )
                                            },
                                        imageUrl = it.posterPath.createImageUrl(),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        sharedTransitionKey = "${it.id}_${context.getString(R.string.top_rated)}",
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.FilmItem(
    modifier: Modifier = Modifier,
    sharedTransitionKey: String,
    animatedVisibilityScope: AnimatedVisibilityScope,
    imageUrl: String,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.ic_placeholder),
        error = painterResource(id = R.drawable.ic_placeholder),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .sharedElement(
                sharedContentState = rememberSharedContentState(
                    key = sharedTransitionKey
                ),
                animatedVisibilityScope = animatedVisibilityScope,
            )
            .fillMaxSize()
            .clip(shape = MaterialTheme.shapes.medium)
    )
}

@Composable
fun FilmCategory(
    items: List<String>,
    modifier: Modifier = Modifier,
    state: HomeUiState,
    onEvent: (HomeUiEvents) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        items.forEach { item ->
            val lineLength = animateFloatAsState(
                targetValue = if (item == state.selectedFilmOption) 2f else 0f,
                animationSpec = tween(
                    durationMillis = 300
                ),
                label = "lineLength"
            )

            val primaryColor = MaterialTheme.colorScheme.primary
            Text(
                text = item,
                modifier = Modifier
                    .padding(8.dp)
                    .drawBehind {
                        if (item == state.selectedFilmOption) {
                            if (lineLength.value > 0f) {
                                drawLine(
                                    color = primaryColor,
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
                        onEvent(
                            HomeUiEvents.OnFilmOptionSelected(
                                item
                            )
                        )
                    },
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = if (item == state.selectedFilmOption) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(
                        .5f
                    ),
                )
            )
        }
    }
}

@Composable
fun Genres(
    state: HomeUiState,
    onEvent: (HomeUiEvents) -> Unit,
) {
    val genres = if (state.selectedFilmOption == "Tv Shows") {
        state.tvSeriesGenres
    } else {
        state.moviesGenres
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = genres) { genre ->
            val isSelected = genre.name == state.selectedGenre?.name

            Box(
                modifier = Modifier
                    .background(
                        color = if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.background
                        },
                        shape = MaterialTheme.shapes.medium
                    )
                    .border(
                        width = .3.dp,
                        shape = MaterialTheme.shapes.medium,
                        color = if (isSelected) {
                            MaterialTheme.colorScheme.background
                        } else {
                            MaterialTheme.colorScheme.onBackground.copy(.5f)
                        }
                    )
                    .clickable {
                        onEvent(
                            HomeUiEvents.OnFilmGenreSelected(
                                genre = genre,
                                filmType = state.selectedFilmOption,
                                selectedFilmOption = state.selectedFilmOption
                            )
                        )
                    }
            ) {
                Text(
                    text = genre.name,
                    modifier = Modifier
                        .padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        ),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = if (isSelected) {
                            MaterialTheme.colorScheme.onPrimary
                        } else {
                            MaterialTheme.colorScheme.onBackground
                        }
                    )
                )
            }
        }
    }
}

@Composable
fun <T : Any> PagedRow(
    modifier: Modifier = Modifier,
    items: LazyPagingItems<T>,
    content: @Composable (T) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(210.dp),
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items.itemCount) {
                val item = items[it]
                if (item != null) {
                    content(item)
                }
            }

            items.loadState.let { loadState ->
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .align(Alignment.Center),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                CircularProgressIndicator(
                                    strokeWidth = 2.dp,
                                )
                            }
                        }
                    }

                    loadState.refresh is LoadState.NotLoading && items.itemCount < 1 -> {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .align(Alignment.Center),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    text = "No data available",
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }


                    loadState.refresh is LoadState.Error -> {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .align(Alignment.Center),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    text = when ((loadState.refresh as LoadState.Error).error) {
                                        is HttpException -> {
                                            "Oops, something went wrong!"
                                        }

                                        is IOException -> {
                                            "Couldn't reach server, check your internet connection!"
                                        }

                                        else -> {
                                            "Unknown error occurred"
                                        }
                                    },
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.primary,
                                )
                            }
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .align(Alignment.Center),
                                    strokeWidth = 2.dp,
                                )
                            }
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "An error occurred",
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun Movie.toFilm(
    category: String,
) = Film(
    id = id,
    type = TYPE_MOVIE,
    image = posterPath.createImageUrl(),
    category = category,
    name = title,
    rating = voteAverage.toFloat(),
    releaseDate = releaseDate,
    overview = overview
)

fun Series.toFilm(
    category: String,
) = Film(
    id = id,
    type = TYPE_TV_SERIES,
    image = posterPath.createImageUrl(),
    category = category,
    name = name,
    rating = voteAverage.toFloat(),
    releaseDate = firstAirDate,
    overview = overview
)
