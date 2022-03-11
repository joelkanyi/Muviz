package com.kanyideveloper.muviz.screens.film_details

import androidx.lifecycle.ViewModel
import com.kanyideveloper.muviz.data.remote.responses.CreditsResponse
import com.kanyideveloper.muviz.data.remote.responses.MovieDetails
import com.kanyideveloper.muviz.data.remote.responses.TvSeriesDetails
import com.kanyideveloper.muviz.data.repository.FilmsRepository
import com.kanyideveloper.muviz.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilmDetailsViewModel @Inject constructor(
private val filmsRepository: FilmsRepository
) : ViewModel() {

    suspend fun getMovieDetails(movieId: Int): Resource<MovieDetails> {
            return filmsRepository.getMoviesDetails(movieId)
    }

    suspend fun getTvSeriesDetails(tvId: Int): Resource<TvSeriesDetails> {
        return filmsRepository.getTvSeriesDetails(tvId)
    }

    suspend fun getMovieCasts(movieId: Int): Resource<CreditsResponse>{
        return filmsRepository.getMovieCasts(movieId)
    }

    suspend fun getTvSeriesCasts(tvId: Int): Resource<CreditsResponse>{
        return filmsRepository.getTvSeriesCasts(tvId)
    }

}