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

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanyideveloper.muviz.cast.domain.usecase.GetMovieCastUseCase
import com.kanyideveloper.muviz.cast.domain.usecase.GetTvCastUseCase
import com.kanyideveloper.muviz.filmdetail.data.repository.FilmsDetailsRepository
import com.kanyideveloper.muviz.common.util.Resource
import com.kanyideveloper.muviz.favorites.data.data.local.Favorite
import com.kanyideveloper.muviz.favorites.domain.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmDetailsViewModel @Inject constructor(
    private val repository: FilmsDetailsRepository,
    private val favoritesRepository: FavoritesRepository,
    private val getTvCastUseCase: GetTvCastUseCase,
    private val getMovieCastUseCase: GetMovieCastUseCase,
) : ViewModel() {
    private val _filmDetailsUiState = MutableStateFlow(FilmDetailsUiState())
    val filmDetailsUiState = _filmDetailsUiState.asStateFlow()

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _filmDetailsUiState.update {
                it.copy(
                    isLoading = true
                )
            }
            when (val result = repository.getMoviesDetails(movieId)) {
                is Resource.Error -> {
                    _filmDetailsUiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }

                is Resource.Success -> {
                    _filmDetailsUiState.update {
                        it.copy(
                            isLoading = false,
                            movieDetails = result.data
                        )
                    }
                }

                else -> {
                    filmDetailsUiState
                }
            }
        }
    }

    fun getTvSeriesDetails(tvId: Int) {
        viewModelScope.launch {
            _filmDetailsUiState.update {
                it.copy(
                    isLoading = true
                )
            }
            when (val result = repository.getTvSeriesDetails(tvId)) {
                is Resource.Error -> {
                    _filmDetailsUiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }

                is Resource.Success -> {
                    _filmDetailsUiState.update {
                        it.copy(
                            isLoading = false,
                            tvSeriesDetails = result.data
                        )
                    }
                }

                else -> {
                    filmDetailsUiState
                }
            }
        }
    }

    fun getMovieCasts(movieId: Int) {
        viewModelScope.launch {
            _filmDetailsUiState.update {
                it.copy(isLoadingCasts = true)
            }
            when (val result = getMovieCastUseCase(movieId)) {
                is Resource.Error -> {
                    _filmDetailsUiState.update {
                        it.copy(
                            isLoadingCasts = false,
                            errorCasts = result.message
                        )
                    }
                }

                is Resource.Success -> {
                    _filmDetailsUiState.update {
                        it.copy(
                            isLoadingCasts = false,
                            credits = result.data
                        )
                    }
                }

                else -> {
                    filmDetailsUiState
                }
            }
        }
    }

    fun getTvSeriesCasts(tvId: Int) {
        viewModelScope.launch {
            _filmDetailsUiState.update {
                it.copy(isLoadingCasts = true)
            }
            when (val result = getTvCastUseCase(tvId)) {
                is Resource.Error -> {
                    _filmDetailsUiState.update {
                        it.copy(
                            isLoadingCasts = false,
                            errorCasts = result.message
                        )
                    }
                }

                is Resource.Success -> {
                    _filmDetailsUiState.update {
                        it.copy(
                            isLoadingCasts = false,
                            credits = result.data
                        )
                    }
                }

                else -> {
                    filmDetailsUiState
                }
            }
        }
    }

    fun getFilmDetails(filmId: Int, filmType: String) {
        if (filmType == "movie") {
            getMovieDetails(filmId)
            getMovieCasts(filmId)
        } else {
            getTvSeriesDetails(filmId)
            getTvSeriesCasts(filmId)
        }
    }

    fun isAFavorite(mediaId: Int): LiveData<Boolean> {
        return favoritesRepository.isFavorite(mediaId)
    }

    fun insertFavorite(favorite: Favorite) {
        viewModelScope.launch {
            favoritesRepository.insertFavorite(favorite)
        }
    }
}
