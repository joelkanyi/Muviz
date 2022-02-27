package com.kanyideveloper.muviz.screens.home

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanyideveloper.muviz.data.remote.responses.Genre
import com.kanyideveloper.muviz.data.remote.responses.Movie
import com.kanyideveloper.muviz.data.remote.responses.MovieDetails
import com.kanyideveloper.muviz.data.remote.responses.Series
import com.kanyideveloper.muviz.data.repository.FilmsRepository
import com.kanyideveloper.muviz.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeScreenViewModel"

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val filmsRepository: FilmsRepository
) : ViewModel() {
    private val _selectedOption = mutableStateOf("Movies")
    val selectedOption: State<String> = _selectedOption

    private val _selectedGenre = mutableStateOf("")
    val selectedGenre: State<String> = _selectedGenre

    fun setSelectedOption(selectedOption: String) {
        _selectedOption.value = selectedOption
    }

    fun setGenre(genre: String) {
        _selectedGenre.value = genre
    }

    private val _popularMovies = mutableStateOf<List<Movie>>(emptyList())
    val popularMovies: State<List<Movie>> = _popularMovies

    private val _popularTvSeries = mutableStateOf<List<Series>>(emptyList())
    val popularTvSeries: State<List<Series>> = _popularTvSeries

    private val _moviesGenres = mutableStateOf<List<Genre>>(emptyList())
    val moviesGenres: State<List<Genre>> = _moviesGenres

    private val _moviesDetails = mutableStateOf(MovieDetails())
    val moviesDetails: State<MovieDetails> = _moviesDetails

    private val _tvSeriesGenres = mutableStateOf<List<Genre>>(emptyList())
    val tvSeriesGenres: State<List<Genre>> = _tvSeriesGenres

    var isLoading = mutableStateOf(false)
    var loadingError = mutableStateOf("")

    init {
        getPopularMovies(null, 1, "en")
        getPopularTvSeries(null, 1, "en")
        getMoviesGenres()
        getSeriesGenres()
    }

    fun getPopularMovies(genreId: Int? = null, page: Int = 1, language: String = "en") {
        isLoading.value = true
        viewModelScope.launch {
            when (val result = filmsRepository.getPopularMovies(page, language)) {
                is Resource.Success -> {
                    _popularMovies.value = if (genreId != null) {
                        result.data?.results!!.filter { it.genreIds.contains(genreId) }
                    } else {
                        result.data?.results!!
                    }
                    isLoading.value = false
                }
                is Resource.Error -> {
                    loadingError.value = result.message.toString()
                    isLoading.value = false
                }
            }
        }
    }

    fun getPopularTvSeries(genreId: Int? = null, page: Int = 1, language: String = "en") {
        //isLoading.value = true
        viewModelScope.launch {
            when (val result = filmsRepository.getPopularTvSeries(page, language)) {
                is Resource.Success -> {
                    _popularTvSeries.value = if (genreId != null) {
                        result.data?.series!!.filter { it.genre_ids.contains(genreId) }
                    } else {
                        result.data?.series!!
                    }
                    //isLoading.value = false
                }
                is Resource.Error -> {
                    loadingError.value = result.message.toString()
                    //isLoading.value = false
                }
            }
        }
    }

    private fun getMoviesGenres() {
        viewModelScope.launch {
            when (val result = filmsRepository.getMoviesGenres("en")) {
                is Resource.Success -> {
                    _moviesGenres.value = result.data?.genres!!
                }
                is Resource.Error -> {
                    //loadingError.value = result.message.toString()
                }
            }
        }
    }

    private fun getSeriesGenres() {
        viewModelScope.launch {
            when (val result = filmsRepository.getSeriesGenres("en")) {
                is Resource.Success -> {
                    _tvSeriesGenres.value = result.data?.genres!!
                }
                is Resource.Error -> {
                    //loadingError.value = result.message.toString()
                }
            }
        }
    }

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            when (val result = filmsRepository.getMoviesDetails(movieId)) {
                is Resource.Success -> {
                    _moviesDetails.value = result.data!!
                }
                is Resource.Error -> {
                    //loadingError.value = result.message.toString()
                }
            }
        }
    }
}