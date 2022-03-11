package com.kanyideveloper.muviz.screens.home

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.kanyideveloper.muviz.data.remote.responses.Genre
import com.kanyideveloper.muviz.data.remote.responses.Movie
import com.kanyideveloper.muviz.data.remote.responses.Series
import com.kanyideveloper.muviz.data.repository.FilmsRepository
import com.kanyideveloper.muviz.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
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
    private var _trendingMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val trendingMovies: State<Flow<PagingData<Movie>>> = _trendingMovies

    private val _upcomingMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val upcomingMovies: State<Flow<PagingData<Movie>>> = _upcomingMovies

    private val _topRatedMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val topRatedMovies: State<Flow<PagingData<Movie>>> = _topRatedMovies

    private val _nowPlayingMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val nowPlayingMovies: State<Flow<PagingData<Movie>>> = _nowPlayingMovies

    private val _popularMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val popularMovies: State<Flow<PagingData<Movie>>> = _popularMovies

    private val _moviesGenres = mutableStateOf<List<Genre>>(emptyList())
    val moviesGenres: State<List<Genre>> = _moviesGenres


    /**
     * Tv Series states
     */
    private val _trendingTvSeries = mutableStateOf<Flow<PagingData<Series>>>(emptyFlow())
    val trendingTvSeries: State<Flow<PagingData<Series>>> = _trendingTvSeries

    private val _onAirTvSeries = mutableStateOf<Flow<PagingData<Series>>>(emptyFlow())
    val onAirTvSeries: State<Flow<PagingData<Series>>> = _onAirTvSeries

    private val _topRatedTvSeries = mutableStateOf<Flow<PagingData<Series>>>(emptyFlow())
    val topRatedTvSeries: State<Flow<PagingData<Series>>> = _topRatedTvSeries

    private val _airingTodayTvSeries = mutableStateOf<Flow<PagingData<Series>>>(emptyFlow())
    val airingTodayTvSeries: State<Flow<PagingData<Series>>> = _airingTodayTvSeries

    private val _popularTvSeries = mutableStateOf<Flow<PagingData<Series>>>(emptyFlow())
    val popularTvSeries: State<Flow<PagingData<Series>>> = _popularTvSeries

    private val _tvSeriesGenres = mutableStateOf<List<Genre>>(emptyList())
    val tvSeriesGenres: State<List<Genre>> = _tvSeriesGenres

    init {
        getTrendingMovies(null)
        getNowPayingMovies(null)
        getUpcomingMovies(null)
        getTopRatedMovies(null)
        getPopularMovies(null)
        getPopularTvSeries(null)
        getMoviesGenres()

        getAiringTodayTvSeries(null)
        getTrendingTvSeries(null)
        getOnTheAirTvSeries(null)
        getTopRatedTvSeries(null)
        getOnTheAirTvSeries(null)
        getSeriesGenres()
    }

    /**
     * Movies
     */
    fun getTrendingMovies(genreId: Int?) {
        viewModelScope.launch {
            _trendingMovies.value = if (genreId != null) {
                filmsRepository.getTrendingMoviesThisWeek().map { pagingData ->
                    pagingData.filter {
                        it.genreIds.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                filmsRepository.getTrendingMoviesThisWeek().cachedIn(viewModelScope)
            }
        }
    }


    fun getUpcomingMovies(genreId: Int?) {
        viewModelScope.launch {
            _upcomingMovies.value = if (genreId != null) {
                filmsRepository.getUpcomingMovies().map { pagingData ->
                    pagingData.filter {
                        it.genreIds.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                filmsRepository.getUpcomingMovies().cachedIn(viewModelScope)
            }
        }
    }

    fun getTopRatedMovies(genreId: Int?) {
        viewModelScope.launch {
            _topRatedMovies.value = if (genreId != null) {
                filmsRepository.getTopRatedMovies().map { pagingData ->
                    pagingData.filter {
                        it.genreIds.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                filmsRepository.getTopRatedMovies().cachedIn(viewModelScope)
            }
        }
    }

    fun getNowPayingMovies(genreId: Int?) {
        viewModelScope.launch {
            _nowPlayingMovies.value = if (genreId != null) {
                filmsRepository.getNowPlayingMovies().map { pagingData ->
                    pagingData.filter {
                        it.genreIds.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                filmsRepository.getNowPlayingMovies().cachedIn(viewModelScope)
            }
        }
    }

    fun getPopularMovies(genreId: Int?) {
        viewModelScope.launch {
            _popularMovies.value = if (genreId != null) {
                filmsRepository.getPopularMovies().map { pagingData ->
                    pagingData.filter {
                        it.genreIds.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                filmsRepository.getPopularMovies().cachedIn(viewModelScope)
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
    fun getTrendingTvSeries(genreId: Int?) {
        viewModelScope.launch {
            _trendingTvSeries.value = if (genreId != null) {
                filmsRepository.getTrendingThisWeekTvSeries().map { pagingData ->
                    pagingData.filter {
                        it.genreIds.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                filmsRepository.getTrendingThisWeekTvSeries().cachedIn(viewModelScope)
            }
        }
    }

    fun getOnTheAirTvSeries(genreId: Int?) {
        viewModelScope.launch {
            _onAirTvSeries.value = if (genreId != null) {
                filmsRepository.getOnTheAirTvSeries().map { pagingData ->
                    pagingData.filter {
                        it.genreIds.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                filmsRepository.getOnTheAirTvSeries().cachedIn(viewModelScope)
            }
        }
    }

    fun getTopRatedTvSeries(genreId: Int?) {
        viewModelScope.launch {
            _topRatedTvSeries.value = if (genreId != null) {
                filmsRepository.getTopRatedTvSeries().map { pagingData ->
                    pagingData.filter {
                        it.genreIds.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                filmsRepository.getTopRatedTvSeries().cachedIn(viewModelScope)
            }
        }
    }

    fun getAiringTodayTvSeries(genreId: Int?) {
        viewModelScope.launch {
            _airingTodayTvSeries.value = if (genreId != null) {
                filmsRepository.getAiringTodayTvSeries().map { pagingData ->
                    pagingData.filter {
                        it.genreIds.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                filmsRepository.getAiringTodayTvSeries().cachedIn(viewModelScope)
            }
        }
    }

    fun getPopularTvSeries(genreId: Int?) {
        viewModelScope.launch {
            _popularTvSeries.value = if (genreId != null) {
                filmsRepository.getPopularTvSeries().map { pagingData ->
                    pagingData.filter {
                        it.genreIds.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                filmsRepository.getPopularTvSeries().cachedIn(viewModelScope)
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