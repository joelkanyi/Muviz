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

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kanyideveloper.muviz.R
import com.kanyideveloper.muviz.common.presentation.components.StandardToolbar
import com.kanyideveloper.muviz.common.presentation.theme.primaryDark
import com.kanyideveloper.muviz.common.presentation.theme.primaryGray
import com.kanyideveloper.muviz.common.presentation.theme.primaryPink
import com.kanyideveloper.muviz.common.util.Constants.IMAGE_BASE_UR
import com.kanyideveloper.muviz.destinations.FilmDetailsScreenDestination
import com.kanyideveloper.muviz.destinations.SearchScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import retrofit2.HttpException
import java.io.IOException

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    HomeScreenContent(
        state = homeUiState,
        onEvent = { event ->
            when (event) {
                is HomeUiEvents.NavigateBack -> {
                    navigator.navigateUp()
                }

                HomeUiEvents.OnSearchClick -> {
                    navigator.navigate(SearchScreenDestination)
                }

                is HomeUiEvents.NavigateToFilmDetails -> {
                    navigator.navigate(
                        FilmDetailsScreenDestination(
                            filmType = event.filmType,
                            filmId = event.id
                        )
                    )
                }

                is HomeUiEvents.OnFilmGenreSelected -> {
                    if (homeUiState.selectedFilmOption == "Movies") {
                        viewModel.setGenre(event.genre)
                        viewModel.getTrendingMovies(event.genre.id)
                        viewModel.getTopRatedMovies(event.genre.id)
                        viewModel.getUpcomingMovies(event.genre.id)
                        viewModel.getNowPayingMovies(event.genre.id)
                        viewModel.getPopularMovies(event.genre.id)
                    } else if (homeUiState.selectedFilmOption == "Tv Shows") {
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreenContent(
    state: HomeUiState,
    onEvent: (HomeUiEvents) -> Unit,
) {
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
                        onEvent(HomeUiEvents.OnSearchClick)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = null,
                            tint = primaryGray
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        val pullRefreshState = rememberPullRefreshState(
            refreshing = false,
            onRefresh = {
                onEvent(HomeUiEvents.OnPullToRefresh)
            }
        )
        Box(
            modifier = Modifier
                .pullRefresh(state = pullRefreshState)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                item {
                    FilmCategory(
                        items = listOf("Movies", "Tv Shows"),
                        modifier = Modifier.fillMaxWidth(),
                        state = state,
                        onEvent = onEvent,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
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
                        state = state,
                        onEvent = onEvent,
                    )
                }

                item(content = {
                    Text(text = "Trending today", color = Color.White, fontSize = 18.sp)

                    Spacer(modifier = Modifier.height(8.dp))
                })
                item(content = {
                    Spacer(modifier = Modifier.height(5.dp))
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(220.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            content = {

                                if (state.selectedFilmOption == "Tv Shows") {
                                    items(
                                        count = trendingTvSeries.itemCount
                                    ) { index ->
                                        val film = trendingTvSeries[index]
                                        FilmItem(
                                            modifier = Modifier
                                                .height(220.dp)
                                                .width(250.dp)
                                                .clickable {
                                                    onEvent(
                                                        HomeUiEvents.NavigateToFilmDetails(
                                                            id = film?.id!!,
                                                            filmType = "tv"
                                                        )
                                                    )
                                                },
                                            imageUrl = "$IMAGE_BASE_UR/${film?.posterPath}"
                                        )
                                    }
                                } else {
                                    items(
                                        count = trendingMovies.itemCount
                                    ) { index ->
                                        val film = trendingMovies[index]
                                        FilmItem(
                                            modifier = Modifier
                                                .height(200.dp)
                                                .width(230.dp)
                                                .clickable {
                                                    onEvent(
                                                        HomeUiEvents.NavigateToFilmDetails(
                                                            id = film?.id!!,
                                                            filmType = "movie"
                                                        )
                                                    )
                                                },
                                            imageUrl = "$IMAGE_BASE_UR/${film?.posterPath}"
                                        )
                                    }
                                }

                                if (trendingMovies.loadState.append == LoadState.Loading) {
                                    item {
                                        CircularProgressIndicator(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .wrapContentWidth(Alignment.CenterHorizontally)
                                        )
                                    }
                                }
                            }
                        )

                        trendingMovies.apply {
                            loadState
                            when (loadState.refresh) {
                                is LoadState.Loading -> {
                                    CircularProgressIndicator(
                                        modifier = Modifier,
                                        color = primaryPink,
                                        strokeWidth = 2.dp
                                    )
                                }

                                is LoadState.Error -> {
                                    val e = trendingMovies.loadState.refresh as LoadState.Error
                                    Text(
                                        text = when (e.error) {
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
                                        textAlign = TextAlign.Center,
                                        color = primaryPink
                                    )
                                }

                                else -> {
                                }
                            }
                        }
                    }
                })

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Popular", color = Color.White, fontSize = 18.sp)
                }

                item {
                    Spacer(modifier = Modifier.height(5.dp))

                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(210.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            if (state.selectedFilmOption == "Tv Shows") {
                                items(
                                    count = popularTvSeries.itemCount
                                ) { index ->
                                    val film = popularTvSeries[index]
                                    FilmItem(
                                        modifier = Modifier
                                            .height(200.dp)
                                            .width(130.dp)
                                            .clickable {
                                                onEvent(
                                                    HomeUiEvents.NavigateToFilmDetails(
                                                        id = film?.id!!,
                                                        filmType = "tv"
                                                    )
                                                )
                                            },
                                        imageUrl = "$IMAGE_BASE_UR/${film?.posterPath}"
                                    )
                                }
                            } else {
                                items(
                                    count = popularMovies.itemCount
                                ) { index ->
                                    val film = popularMovies[index]
                                    FilmItem(
                                        modifier = Modifier
                                            .height(200.dp)
                                            .width(130.dp)
                                            .clickable {
                                                onEvent(
                                                    HomeUiEvents.NavigateToFilmDetails(
                                                        id = film?.id!!,
                                                        filmType = "movie"
                                                    )
                                                )
                                            },
                                        imageUrl = "$IMAGE_BASE_UR/${film?.posterPath}"
                                    )
                                }
                            }

                            if (popularMovies.loadState.append == LoadState.Loading) {
                                item {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentWidth(Alignment.CenterHorizontally)
                                    )
                                }
                            }
                        }

                        popularMovies.apply {
                            loadState
                            when (loadState.refresh) {
                                is LoadState.Loading -> {
                                    CircularProgressIndicator(
                                        modifier = Modifier,
                                        color = primaryPink,
                                        strokeWidth = 2.dp
                                    )
                                }

                                is LoadState.Error -> {
                                    val e = popularMovies.loadState.refresh as LoadState.Error
                                    Text(
                                        text = when (e.error) {
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
                                        textAlign = TextAlign.Center,
                                        color = primaryPink
                                    )
                                }

                                else -> {
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (state.selectedFilmOption == "Tv Shows") {
                            "On Air"
                        } else {
                            "Upcoming"
                        },
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(5.dp))

                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(210.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            content = {
                                if (state.selectedFilmOption == "Tv Shows") {
                                    items(
                                        count = onAirTvSeries.itemCount
                                    ) { index ->
                                        val film = onAirTvSeries[index]
                                        FilmItem(
                                            modifier = Modifier
                                                .height(200.dp)
                                                .width(130.dp)
                                                .clickable {
                                                    onEvent(
                                                        HomeUiEvents.NavigateToFilmDetails(
                                                            id = film?.id!!,
                                                            filmType = "tv"
                                                        )
                                                    )
                                                },
                                            imageUrl = "$IMAGE_BASE_UR/${film?.posterPath}"
                                        )
                                    }
                                } else {
                                    items(
                                        count = upcomingMovies.itemCount
                                    ) { index ->
                                        val film = upcomingMovies[index]
                                        FilmItem(
                                            modifier = Modifier
                                                .height(200.dp)
                                                .width(130.dp)
                                                .clickable {
                                                    onEvent(
                                                        HomeUiEvents.NavigateToFilmDetails(
                                                            id = film?.id!!,
                                                            filmType = "movie"
                                                        )
                                                    )
                                                },
                                            imageUrl = "$IMAGE_BASE_UR/${film?.posterPath}"
                                        )
                                    }
                                }
                                if (upcomingMovies.loadState.append == LoadState.Loading) {
                                    item {
                                        CircularProgressIndicator(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .wrapContentWidth(Alignment.CenterHorizontally)
                                        )
                                    }
                                }
                            }
                        )

                        upcomingMovies.apply {
                            loadState
                            when (loadState.refresh) {
                                is LoadState.Loading -> {
                                    CircularProgressIndicator(
                                        modifier = Modifier,
                                        color = primaryPink,
                                        strokeWidth = 2.dp
                                    )
                                }

                                is LoadState.Error -> {
                                    val e = upcomingMovies.loadState.refresh as LoadState.Error
                                    Text(
                                        text = when (e.error) {
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
                                        textAlign = TextAlign.Center,
                                        color = primaryPink
                                    )
                                }

                                else -> {}
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (state.selectedFilmOption == "Tv Shows") {
                            "Airing today"
                        } else {
                            "Now playing"
                        },
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(5.dp))

                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(210.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            content = {
                                if (state.selectedFilmOption == "Tv Shows") {
                                    items(
                                        count = airingTodayTvSeries.itemCount
                                    ) { index ->
                                        val film = airingTodayTvSeries[index]
                                        FilmItem(
                                            modifier = Modifier
                                                .height(200.dp)
                                                .width(130.dp)
                                                .clickable {
                                                    onEvent(
                                                        HomeUiEvents.NavigateToFilmDetails(
                                                            id = film?.id!!,
                                                            filmType = "tv"
                                                        )
                                                    )
                                                },
                                            imageUrl = "$IMAGE_BASE_UR/${film?.posterPath}"
                                        )
                                    }
                                } else {
                                    items(
                                        count = nowPlayingMovies.itemCount
                                    ) { index ->
                                        val film = nowPlayingMovies[index]
                                        FilmItem(
                                            modifier = Modifier
                                                .height(200.dp)
                                                .width(130.dp)
                                                .clickable {
                                                    onEvent(
                                                        HomeUiEvents.NavigateToFilmDetails(
                                                            id = film?.id!!,
                                                            filmType = "movie"
                                                        )
                                                    )
                                                },
                                            imageUrl = "$IMAGE_BASE_UR/${film?.posterPath}"
                                        )
                                    }
                                }
                                if (nowPlayingMovies.loadState.append == LoadState.Loading) {
                                    item {
                                        CircularProgressIndicator(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .wrapContentWidth(Alignment.CenterHorizontally)
                                        )
                                    }
                                }
                            })

                        nowPlayingMovies.apply {
                            loadState
                            when (loadState.refresh) {
                                is LoadState.Loading -> {
                                    CircularProgressIndicator(
                                        modifier = Modifier,
                                        color = primaryPink,
                                        strokeWidth = 2.dp
                                    )
                                }

                                is LoadState.Error -> {
                                    val e = nowPlayingMovies.loadState.refresh as LoadState.Error
                                    Text(
                                        text = when (e.error) {
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
                                        textAlign = TextAlign.Center,
                                        color = primaryPink
                                    )
                                }

                                else -> {}
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Top rated",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(5.dp))

                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(210.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            content = {
                                if (state.selectedFilmOption == "Tv Shows") {
                                    items(
                                        count = topRatedTvSeries.itemCount
                                    ) { index ->
                                        val film = topRatedTvSeries[index]
                                        FilmItem(
                                            modifier = Modifier
                                                .height(200.dp)
                                                .width(130.dp)
                                                .clickable {
                                                    onEvent(
                                                        HomeUiEvents.NavigateToFilmDetails(
                                                            id = film?.id!!,
                                                            filmType = "tv"
                                                        )
                                                    )
                                                },
                                            imageUrl = "$IMAGE_BASE_UR/${film?.posterPath}"
                                        )
                                    }
                                } else {
                                    items(
                                        count = topRatedMovies.itemCount
                                    ) { index ->
                                        val film = topRatedMovies[index]
                                        FilmItem(
                                            modifier = Modifier
                                                .height(200.dp)
                                                .width(130.dp)
                                                .clickable {
                                                    onEvent(
                                                        HomeUiEvents.NavigateToFilmDetails(
                                                            id = film?.id!!,
                                                            filmType = "movie"
                                                        )
                                                    )
                                                },
                                            imageUrl = "$IMAGE_BASE_UR/${film?.posterPath}"
                                        )
                                    }
                                }
                                if (topRatedMovies.loadState.append == LoadState.Loading) {
                                    item {
                                        CircularProgressIndicator(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .wrapContentWidth(Alignment.CenterHorizontally)
                                        )
                                    }
                                }
                            })

                        topRatedMovies.apply {
                            loadState
                            when (loadState.refresh) {
                                is LoadState.Loading -> {
                                    CircularProgressIndicator(
                                        modifier = Modifier,
                                        color = primaryPink,
                                        strokeWidth = 2.dp
                                    )
                                }

                                is LoadState.Error -> {
                                    val e = topRatedMovies.loadState.refresh as LoadState.Error
                                    Text(
                                        text = when (e.error) {
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
                                        textAlign = TextAlign.Center,
                                        color = primaryPink
                                    )
                                }

                                else -> {}
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
            PullRefreshIndicator(
                refreshing = false,
                pullRefreshState,
                Modifier.align(Alignment.TopCenter),
            )
        }
    }
}

@Composable
fun FilmItem(
    modifier: Modifier = Modifier,
    imageUrl: String
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

            Text(
                text = item,
                color = if (item == state.selectedFilmOption) Color.White else primaryGray,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(8.dp)
                    .drawBehind {
                        if (item == state.selectedFilmOption) {
                            if (lineLength.value > 0f) {
                                drawLine(
                                    color = primaryPink,
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
                    }
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
                        onEvent(
                            HomeUiEvents.OnFilmGenreSelected(
                                genre = genre,
                                filmType = state.selectedFilmOption,
                                selectedFilmOption = state.selectedFilmOption
                            )
                        )
                    }
                    .background(
                        if (genre.name == state.selectedGenre?.name) {
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
