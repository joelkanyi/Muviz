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
package com.kanyideveloper.muviz.search.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kanyideveloper.muviz.R
import com.kanyideveloper.muviz.common.presentation.components.StandardToolbar
import com.kanyideveloper.muviz.common.presentation.theme.MuvizTheme
import com.kanyideveloper.muviz.common.presentation.theme.primaryDarkVariant
import com.kanyideveloper.muviz.common.presentation.theme.primaryGray
import com.kanyideveloper.muviz.common.presentation.theme.primaryPink
import com.kanyideveloper.muviz.search.domain.model.Search
import com.kanyideveloper.muviz.common.util.Constants
import com.kanyideveloper.muviz.destinations.FilmDetailsScreenDestination
import com.kanyideveloper.muviz.genre.domain.model.Genre
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import retrofit2.HttpException
import java.io.IOException

@OptIn(androidx.compose.ui.ExperimentalComposeUiApi::class)
@Destination(start = false)
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchUiState by viewModel.searchUiState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = Unit) {
        viewModel.getMoviesGenres()
        viewModel.getTvSeriesGenres()
    }

    SearchScreenContent(
        state = searchUiState,
        onEvent = { event ->
            when (event) {
                is SearchUiEvents.NavigateBack -> {
                    navigator.popBackStack()
                }

                is SearchUiEvents.SearchFilm -> {
                    viewModel.searchAll(event.searchTerm)
                    keyboardController?.hide()
                }

                is SearchUiEvents.SearchTermChanged -> {
                    viewModel.updateSearchTerm(event.value)
                    viewModel.searchAll(event.value)
                }

                is SearchUiEvents.OpenFilmDetails -> {
                    val search = event.search
                    if (search != null) {
                        navigator.navigate(
                            FilmDetailsScreenDestination(
                                filmId = search.id,
                                filmType = search.mediaType
                            )
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun SearchScreenContent(
    state: SearchUiState,
    onEvent: (SearchUiEvents) -> Unit,
) {
    val searchResult = state.searchResult.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            StandardToolbar(
                onBackArrowClicked = {
                    onEvent(SearchUiEvents.NavigateBack)
                },
                title = {
                    Text(
                        text = stringResource(R.string.search_title),
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                showBackArrow = true
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            val focusRequester = remember { FocusRequester() }
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
            SearchBar(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth()
                    .height(56.dp),
                onEvent = onEvent,
                state = state,
            )
            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(
                        count = searchResult.itemCount,
                    ) { index ->
                        val search = searchResult[index]
                        SearchItem(
                            search = search,
                            state = state,
                            onClick = {
                                onEvent(SearchUiEvents.OpenFilmDetails(search))
                            }
                        )
                    }

                    if (searchResult.loadState.append == LoadState.Loading) {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }

                searchResult.apply {
                    when (loadState.refresh) {
                        is LoadState.Error -> {
                            val e = searchResult.loadState.refresh as LoadState.Error
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
                                modifier = Modifier
                                    .align(alignment = Alignment.Center)
                                    .padding(12.dp),
                                textAlign = TextAlign.Center,
                                color = primaryPink
                            )
                        }

                        is LoadState.NotLoading -> {
                            if (searchResult.itemCount <= 0) {
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
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    state: SearchUiState,
    onEvent: (SearchUiEvents) -> Unit,
) {
    TextField(
        modifier = modifier,
        value = state.searchTerm,
        onValueChange = {
            onEvent(SearchUiEvents.SearchTermChanged(it))
        },
        placeholder = {
            Text(
                text = stringResource(R.string.search),
                color = primaryGray
            )
        },
        shape = MaterialTheme.shapes.large,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            autoCorrect = true,
            keyboardType = KeyboardType.Text,
        ),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            disabledTextColor = Color.Transparent,
            backgroundColor = primaryDarkVariant,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        textStyle = TextStyle(color = Color.White),
        maxLines = 1,
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = {
                onEvent(SearchUiEvents.SearchFilm(searchTerm = state.searchTerm))
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = primaryGray,
                    contentDescription = null
                )
            }
        },
    )
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchItem(
    modifier: Modifier = Modifier,
    state: SearchUiState,
    search: Search?,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(8.dp),
        elevation = 5.dp,
    ) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${Constants.IMAGE_BASE_UR}/${search?.posterPath}")
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_placeholder),
                error = painterResource(id = R.drawable.ic_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth(0.35f),
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row {
                    Text(
                        modifier = Modifier.fillMaxWidth(.7f),
                        text = (search?.name ?: search?.originalName ?: search?.originalTitle
                        ?: "No title provided"),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )

                    (search?.firstAirDate ?: search?.releaseDate)?.let {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Right,
                            text = it,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 10.sp
                        )
                    }
                }


                Spacer(modifier = Modifier.height(8.dp))

                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                ) {

                    val moviesGenres = state.moviesGenres
                    val seriesGenres = state.tvSeriesGenres

                    var searchGenres: List<Genre> = emptyList()
                    if (search?.mediaType == "tv") {
                        searchGenres = seriesGenres.filter {
                            search.genreIds?.contains(it.id)!!
                        }
                    }
                    if (search?.mediaType == "movie") {
                        searchGenres = moviesGenres.filter {
                            search.genreIds?.contains(it.id)!!
                        }
                    }

                    for (genre in searchGenres) {
                        GenreComponent(
                            genre = genre,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = search?.overview ?: "No description",
                    color = Color.White,
                    fontWeight = FontWeight.Light,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
fun GenreComponent(
    modifier: Modifier = Modifier,
    genre: Genre,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .border(
                width = .5.dp,
                color = primaryPink.copy(alpha = 0.5f),
                shape = RoundedCornerShape(50)
            ),
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 2.dp),
            text = genre.name,
            color = primaryPink,
            fontWeight = FontWeight.Normal,
            fontSize = 8.sp
        )
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    MuvizTheme {
        SearchScreenContent(
            state = SearchUiState(),
            onEvent = {}
        )
    }
}
