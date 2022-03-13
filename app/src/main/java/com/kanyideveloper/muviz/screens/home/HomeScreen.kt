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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.kanyideveloper.muviz.R
import com.kanyideveloper.muviz.screens.commons.MovieItem
import com.kanyideveloper.muviz.presentation.components.StandardToolbar
import com.kanyideveloper.muviz.screens.destinations.MovieDetailsScreenDestination
import com.kanyideveloper.muviz.screens.destinations.SearchScreenDestination
import com.kanyideveloper.muviz.screens.destinations.TvSeriesDetailsScreenDestination
import com.kanyideveloper.muviz.ui.theme.lightGray
import com.kanyideveloper.muviz.ui.theme.primaryDark
import com.kanyideveloper.muviz.ui.theme.primaryGray
import com.kanyideveloper.muviz.ui.theme.primaryPink
import com.kanyideveloper.muviz.util.Constants.IMAGE_BASE_UR
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import retrofit2.HttpException
import java.io.IOException

@Destination(start = false)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val trendingMovies = viewModel.trendingMovies.value.collectAsLazyPagingItems()
    val upcomingMovies = viewModel.upcomingMovies.value.collectAsLazyPagingItems()
    val topRatedMovies = viewModel.topRatedMovies.value.collectAsLazyPagingItems()
    val nowPlayingMovies = viewModel.nowPlayingMovies.value.collectAsLazyPagingItems()
    val popularMovies = viewModel.popularMovies.value.collectAsLazyPagingItems()

    val trendingTvSeries = viewModel.trendingTvSeries.value.collectAsLazyPagingItems()
    val onAirTvSeries = viewModel.onAirTvSeries.value.collectAsLazyPagingItems()
    val topRatedTvSeries = viewModel.topRatedTvSeries.value.collectAsLazyPagingItems()
    val airingTodayTvSeries = viewModel.airingTodayTvSeries.value.collectAsLazyPagingItems()
    val popularTvSeries = viewModel.popularTvSeries.value.collectAsLazyPagingItems()

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
                FilmCategory(
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
                    LazyRow(content = {

                        if (viewModel.selectedOption.value == "Tv Shows") {
                            items(trendingTvSeries) { film ->

                                MovieItem(
                                    cardModifier = Modifier
                                        .height(220.dp)
                                        .width(250.dp)
                                        .clickable {
                                            navigator.navigate(TvSeriesDetailsScreenDestination(film?.id!!))
                                        },
                                    imageUrl = "$IMAGE_BASE_UR/${film?.posterPath}"
                                )
                            }
                        } else {
                            items(trendingMovies) { film ->
                                MovieItem(
                                    cardModifier = Modifier
                                        .height(200.dp)
                                        .width(230.dp)
                                        .clickable {
                                            navigator.navigate(MovieDetailsScreenDestination(film?.id!!))
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
                                Unit
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
                    LazyRow {
                        if (viewModel.selectedOption.value == "Tv Shows") {
                            items(popularTvSeries) { film ->

                                MovieItem(
                                    cardModifier = Modifier
                                        .height(200.dp)
                                        .width(130.dp)
                                        .clickable {
                                            navigator.navigate(TvSeriesDetailsScreenDestination(film?.id!!))
                                        },
                                    imageUrl = "$IMAGE_BASE_UR/${film?.posterPath}"
                                )
                            }
                        } else {
                            items(popularMovies) { film ->

                                MovieItem(
                                    cardModifier = Modifier
                                        .height(200.dp)
                                        .width(130.dp)
                                        .clickable {
                                            navigator.navigate(MovieDetailsScreenDestination(film?.id!!))
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
                                Unit
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (viewModel.selectedOption.value == "Tv Shows") {
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
                    LazyRow(content = {
                        if (viewModel.selectedOption.value == "Tv Shows") {
                            items(onAirTvSeries) { film ->

                                MovieItem(
                                    cardModifier = Modifier
                                        .height(200.dp)
                                        .width(130.dp)
                                        .clickable {
                                            navigator.navigate(MovieDetailsScreenDestination(film?.id!!))
                                        },
                                    imageUrl = "$IMAGE_BASE_UR/${film?.posterPath}"
                                )
                            }
                        } else {
                            items(upcomingMovies) { film ->

                                MovieItem(
                                    cardModifier = Modifier
                                        .height(200.dp)
                                        .width(130.dp)
                                        .clickable {
                                            navigator.navigate(MovieDetailsScreenDestination(film?.id!!))
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
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (viewModel.selectedOption.value == "Tv Shows") {
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
                    LazyRow(content = {
                        if (viewModel.selectedOption.value == "Tv Shows") {
                            items(airingTodayTvSeries) { film ->

                                MovieItem(
                                    cardModifier = Modifier
                                        .height(200.dp)
                                        .width(130.dp)
                                        .clickable {
                                            navigator.navigate(MovieDetailsScreenDestination(film?.id!!))
                                        },
                                    imageUrl = "$IMAGE_BASE_UR/${film?.posterPath}"
                                )
                            }
                        } else {
                            items(nowPlayingMovies) { film ->

                                MovieItem(
                                    cardModifier = Modifier
                                        .height(200.dp)
                                        .width(130.dp)
                                        .clickable {
                                            navigator.navigate(MovieDetailsScreenDestination(film?.id!!))
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
                    LazyRow(content = {
                        if (viewModel.selectedOption.value == "Tv Shows") {
                            items(topRatedTvSeries) { film ->

                                MovieItem(
                                    cardModifier = Modifier
                                        .height(200.dp)
                                        .width(130.dp)
                                        .clickable {
                                            navigator.navigate(MovieDetailsScreenDestination(film?.id!!))
                                        },
                                    imageUrl = "$IMAGE_BASE_UR/${film?.posterPath}"
                                )
                            }
                        } else {
                            items(topRatedMovies) { film ->

                                MovieItem(
                                    cardModifier = Modifier
                                        .height(200.dp)
                                        .width(130.dp)
                                        .clickable {
                                            navigator.navigate(MovieDetailsScreenDestination(film?.id!!))
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
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun FilmCategory(
    items: List<String>,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
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
    viewModel: HomeViewModel
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
                        if (viewModel.selectedOption.value == "Movies") {
                            viewModel.setGenre(genre.name)
                            viewModel.getTrendingMovies(genre.id)
                            viewModel.getTopRatedMovies(genre.id)
                            viewModel.getUpcomingMovies(genre.id)
                            viewModel.getNowPayingMovies(genre.id)
                            viewModel.getPopularMovies(genre.id)
                        } else if (viewModel.selectedOption.value == "Tv Shows") {
                            viewModel.setGenre(genre.name)
                            viewModel.getTrendingTvSeries(genre.id)
                            viewModel.getTopRatedTvSeries(genre.id)
                            viewModel.getAiringTodayTvSeries(genre.id)
                            viewModel.getOnTheAirTvSeries(genre.id)
                            viewModel.getPopularTvSeries(genre.id)
                        }
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