package com.kanyideveloper.muviz.screens.home

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

    /**
     * Movies states
     */
    private val _trendingMovies = mutableStateOf<List<Movie>>(emptyList())
    val trendingMovies: State<List<Movie>> = _trendingMovies

    private val _upcomingMovies = mutableStateOf<List<Movie>>(emptyList())
    val upcomingMovies: State<List<Movie>> = _upcomingMovies

    private val _topRatedMovies = mutableStateOf<List<Movie>>(emptyList())
    val topRatedMovies: State<List<Movie>> = _topRatedMovies

    private val _nowPlayingMovies = mutableStateOf<List<Movie>>(emptyList())
    val nowPlayingMovies: State<List<Movie>> = _nowPlayingMovies

    private val _popularMovies = mutableStateOf<List<Movie>>(emptyList())
    val popularMovies: State<List<Movie>> = _popularMovies

    private val _moviesGenres = mutableStateOf<List<Genre>>(emptyList())
    val moviesGenres: State<List<Genre>> = _moviesGenres

    var isLoadingTrendingMovies = mutableStateOf(false)
    var isLoadingUpcomingMovies = mutableStateOf(false)
    var isLoadingTopRatedMovies = mutableStateOf(false)
    var isLoadingNowPlayingMovies = mutableStateOf(false)
    var isLoadingPopularMovies = mutableStateOf(false)


    /**
     * Tv Series states
     */
    private val _trendingTvSeries = mutableStateOf<List<Series>>(emptyList())
    val trendingTvSeries: State<List<Series>> = _trendingTvSeries

    private val _onAirTvSeries = mutableStateOf<List<Series>>(emptyList())
    val onAirTvSeries: State<List<Series>> = _onAirTvSeries

    private val _topRatedTvSeries = mutableStateOf<List<Series>>(emptyList())
    val topRatedTvSeries: State<List<Series>> = _topRatedTvSeries

    private val _airingTodayTvSeries = mutableStateOf<List<Series>>(emptyList())
    val airingTodayTvSeries: State<List<Series>> = _airingTodayTvSeries

    private val _popularTvSeries = mutableStateOf<List<Series>>(emptyList())
    val popularTvSeries: State<List<Series>> = _popularTvSeries

    private val _tvSeriesGenres = mutableStateOf<List<Genre>>(emptyList())
    val tvSeriesGenres: State<List<Genre>> = _tvSeriesGenres

    var isLoadingTrendingSeries = mutableStateOf(false)
    var isLoadingOnAirSeries = mutableStateOf(false)
    var isLoadingTopRatedSeries = mutableStateOf(false)
    var isLoadingAiringTodaySeries = mutableStateOf(false)
    var isLoadingPopularSeries = mutableStateOf(false)

    var loadingError = mutableStateOf("")

    init {
        getPopularMovies(null, 1, "en")
        getPopularTvSeries(null, 1, "en")
        getMoviesGenres()
        getSeriesGenres()
    }

    /**
     * Movies
     */
    fun getTrendingMovies(genreId: Int? = null, page: Int = 1, language: String = "en") {
        isLoadingTrendingMovies.value = true
        viewModelScope.launch {
            when (val result = filmsRepository.getTrendingMoviesThisWeek(page, language)) {
                is Resource.Success -> {
                    _trendingMovies.value = if (genreId != null) {
                        result.data?.results!!.filter { it.genreIds.contains(genreId) }
                    } else {
                        result.data?.results!!
                    }
                    isLoadingTrendingMovies.value = false
                }
                is Resource.Error -> {
                    loadingError.value = result.message.toString()
                    isLoadingTrendingMovies.value = false
                }
            }
        }
    }

    fun getUpcomingMovies(genreId: Int? = null, page: Int = 1, language: String = "en") {
        isLoadingUpcomingMovies.value = true
        viewModelScope.launch {
            when (val result = filmsRepository.getUpcomingMovies(page, language)) {
                is Resource.Success -> {
                    _upcomingMovies.value = if (genreId != null) {
                        result.data?.results!!.filter { it.genreIds.contains(genreId) }
                    } else {
                        result.data?.results!!
                    }
                    isLoadingUpcomingMovies.value = false
                }
                is Resource.Error -> {
                    loadingError.value = result.message.toString()
                    isLoadingUpcomingMovies.value = false
                }
            }
        }
    }

    fun getTopRatedMovies(genreId: Int? = null, page: Int = 1, language: String = "en") {
        isLoadingTopRatedMovies.value = true
        viewModelScope.launch {
            when (val result = filmsRepository.getTopRatedMovies(page, language)) {
                is Resource.Success -> {
                    _topRatedMovies.value = if (genreId != null) {
                        result.data?.results!!.filter { it.genreIds.contains(genreId) }
                    } else {
                        result.data?.results!!
                    }
                    isLoadingTopRatedMovies.value = false
                }
                is Resource.Error -> {
                    loadingError.value = result.message.toString()
                    isLoadingTopRatedMovies.value = false
                }
            }
        }
    }

    fun getNowPayingMovies(genreId: Int? = null, page: Int = 1, language: String = "en") {
        isLoadingNowPlayingMovies.value = true
        viewModelScope.launch {
            when (val result = filmsRepository.getNowPlayingMovies(page, language)) {
                is Resource.Success -> {
                    _nowPlayingMovies.value = if (genreId != null) {
                        result.data?.results!!.filter { it.genreIds.contains(genreId) }
                    } else {
                        result.data?.results!!
                    }
                    isLoadingNowPlayingMovies.value = false
                }
                is Resource.Error -> {
                    loadingError.value = result.message.toString()
                    isLoadingNowPlayingMovies.value = false
                }
            }
        }
    }

    fun getPopularMovies(genreId: Int? = null, page: Int = 1, language: String = "en") {
        isLoadingPopularMovies.value = true
        viewModelScope.launch {
            when (val result = filmsRepository.getPopularMovies(page, language)) {
                is Resource.Success -> {
                    _popularMovies.value = if (genreId != null) {
                        result.data?.results!!.filter { it.genreIds.contains(genreId) }
                    } else {
                        result.data?.results!!
                    }
                    isLoadingPopularMovies.value = false
                }
                is Resource.Error -> {
                    loadingError.value = result.message.toString()
                    isLoadingPopularMovies.value = false
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

    /**
     * Tv Series
     */
    fun getTrendingTvSeries(genreId: Int? = null, page: Int = 1, language: String = "en") {
        isLoadingTrendingSeries.value = true
        viewModelScope.launch {
            when (val result = filmsRepository.getTrendingThisWeekTvSeries(page, language)) {
                is Resource.Success -> {
                    _trendingTvSeries.value = if (genreId != null) {
                        result.data?.series!!.filter { it.genre_ids.contains(genreId) }
                    } else {
                        result.data?.series!!
                    }
                    isLoadingTrendingSeries.value = false
                }
                is Resource.Error -> {
                    loadingError.value = result.message.toString()
                    isLoadingTrendingSeries.value = false
                }
            }
        }
    }

    fun getOnTheAirTvSeries(genreId: Int? = null, page: Int = 1, language: String = "en") {
        isLoadingOnAirSeries.value = true
        viewModelScope.launch {
            when (val result = filmsRepository.getOnTheAirTvSeries(page, language)) {
                is Resource.Success -> {
                    _onAirTvSeries.value = if (genreId != null) {
                        result.data?.series!!.filter { it.genre_ids.contains(genreId) }
                    } else {
                        result.data?.series!!
                    }
                    isLoadingOnAirSeries.value = false
                }
                is Resource.Error -> {
                    loadingError.value = result.message.toString()
                    isLoadingOnAirSeries.value = false
                }
            }
        }
    }

    fun getTopRatedTvSeries(genreId: Int? = null, page: Int = 1, language: String = "en") {
        isLoadingTopRatedSeries.value = true
        viewModelScope.launch {
            when (val result = filmsRepository.getTopRatedTvSeries(page, language)) {
                is Resource.Success -> {
                    _topRatedTvSeries.value = if (genreId != null) {
                        result.data?.series!!.filter { it.genre_ids.contains(genreId) }
                    } else {
                        result.data?.series!!
                    }
                    isLoadingTopRatedSeries.value = false
                }
                is Resource.Error -> {
                    loadingError.value = result.message.toString()
                    isLoadingTopRatedSeries.value = false
                }
            }
        }
    }

    fun getAiringTodayTvSeries(genreId: Int? = null, page: Int = 1, language: String = "en") {
        isLoadingAiringTodaySeries.value = true
        viewModelScope.launch {
            when (val result = filmsRepository.getAiringTodayTvSeries(page, language)) {
                is Resource.Success -> {
                    _airingTodayTvSeries.value = if (genreId != null) {
                        result.data?.series!!.filter { it.genre_ids.contains(genreId) }
                    } else {
                        result.data?.series!!
                    }
                    isLoadingAiringTodaySeries.value = false
                }
                is Resource.Error -> {
                    loadingError.value = result.message.toString()
                    isLoadingAiringTodaySeries.value = false
                }
            }
        }
    }

    fun getPopularTvSeries(genreId: Int? = null, page: Int = 1, language: String = "en") {
        isLoadingPopularSeries.value = true
        viewModelScope.launch {
            when (val result = filmsRepository.getPopularTvSeries(page, language)) {
                is Resource.Success -> {
                    _popularTvSeries.value = if (genreId != null) {
                        result.data?.series!!.filter { it.genre_ids.contains(genreId) }
                    } else {
                        result.data?.series!!
                    }
                    isLoadingPopularSeries.value = false
                }
                is Resource.Error -> {
                    loadingError.value = result.message.toString()
                    isLoadingPopularSeries.value = false
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
}