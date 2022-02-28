package com.kanyideveloper.muviz.screens.film_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanyideveloper.muviz.data.remote.responses.MovieDetails
import com.kanyideveloper.muviz.data.repository.FilmsRepository
import com.kanyideveloper.muviz.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmDetailsViewModel @Inject constructor(
private val filmsRepository: FilmsRepository
) : ViewModel() {

    suspend fun getMovieDetails(movieId: Int): Resource<MovieDetails> {
       // viewModelScope.launch {
/*            when (val result = filmsRepository.getMoviesDetails(movieId)) {
                is Resource.Success -> {
                    _moviesDetails.value = result.data!!
                }
                is Resource.Error -> {
                    //loadingError.value = result.message.toString()
                }*/
            return filmsRepository.getMoviesDetails(movieId)
           // }
        }

}