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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kanyideveloper.muviz.common.util.Resource
import com.kanyideveloper.muviz.genre.domain.usecase.GetMovieGenresUseCase
import com.kanyideveloper.muviz.genre.domain.usecase.GetTvSeriesGenresUseCase
import com.kanyideveloper.muviz.search.domain.usecase.SearchFilmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getMovieGenresUseCase: GetMovieGenresUseCase,
    private val getTvSeriesGenresUseCase: GetTvSeriesGenresUseCase,
    private val searchFilmUseCase: SearchFilmUseCase,
) : ViewModel() {

    private val _searchUiState = MutableStateFlow(SearchUiState())
    val searchUiState = _searchUiState.asStateFlow()

    private var searchJob: Job? = null

    fun searchAll(searchParam: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _searchUiState.update {
                it.copy(
                    searchResult = searchFilmUseCase(searchParam).cachedIn(viewModelScope)
                )
            }
        }
    }

    fun updateSearchTerm(value: String) {
        _searchUiState.update {
            it.copy(searchTerm = value)
        }
    }

    fun getMoviesGenres() {
        viewModelScope.launch {
            _searchUiState.update {
                it.copy(
                    isLoadingGenres = true,
                )
            }
            when (val result = getMovieGenresUseCase()) {
                is Resource.Error -> {
                    _searchUiState.update {
                        it.copy(
                            isLoadingGenres = false,
                        )
                    }
                }

                is Resource.Success -> {
                    _searchUiState.update {
                        it.copy(
                            isLoadingGenres = false,
                            moviesGenres = result.data ?: emptyList()
                        )
                    }
                }

                is Resource.Loading -> {
                    searchUiState
                }
            }
        }
    }

    fun getTvSeriesGenres() {
        viewModelScope.launch {
            _searchUiState.update {
                it.copy(
                    isLoadingGenres = true,
                )
            }
            when (val result = getTvSeriesGenresUseCase()) {
                is Resource.Error -> {
                    _searchUiState.update {
                        it.copy(
                            isLoadingGenres = false,
                        )
                    }
                }

                is Resource.Success -> {
                    _searchUiState.update {
                        it.copy(
                            isLoadingGenres = false,
                            tvSeriesGenres = result.data ?: emptyList()
                        )
                    }
                }

                is Resource.Loading -> {
                    searchUiState
                }
            }
        }
    }
}
