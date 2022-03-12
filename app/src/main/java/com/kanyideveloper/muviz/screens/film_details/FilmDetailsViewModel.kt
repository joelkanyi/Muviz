package com.kanyideveloper.muviz.screens.film_details

import androidx.lifecycle.ViewModel
import com.kanyideveloper.muviz.data.remote.responses.CreditsResponse
import com.kanyideveloper.muviz.data.remote.responses.MovieDetails
import com.kanyideveloper.muviz.data.remote.responses.TvSeriesDetails
import com.kanyideveloper.muviz.data.repository.FilmsDetailsRepository
import com.kanyideveloper.muviz.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FilmDetailsViewModel @Inject constructor(
private val repository: FilmsDetailsRepository
) : ViewModel() {

    suspend fun getMovieDetails(movieId: Int): Resource<MovieDetails> {
            return repository.getMoviesDetails(movieId)
    }

    suspend fun getTvSeriesDetails(tvId: Int): Resource<TvSeriesDetails> {
        return repository.getTvSeriesDetails(tvId)
    }

    suspend fun getMovieCasts(movieId: Int): Resource<CreditsResponse>{
        val cast = repository.getMovieCasts(movieId)
        Timber.d(cast.data.toString())
        return cast
    }

    suspend fun getTvSeriesCasts(tvId: Int): Resource<CreditsResponse>{
        return repository.getTvSeriesCasts(tvId)
    }
}