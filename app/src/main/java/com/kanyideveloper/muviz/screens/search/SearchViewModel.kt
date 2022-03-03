package com.kanyideveloper.muviz.screens.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanyideveloper.muviz.data.remote.responses.Movie
import com.kanyideveloper.muviz.data.remote.responses.Search
import com.kanyideveloper.muviz.data.remote.responses.SearchResponse
import com.kanyideveloper.muviz.data.remote.responses.debug.Result
import com.kanyideveloper.muviz.data.repository.FilmsRepository
import com.kanyideveloper.muviz.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val filmsRepository: FilmsRepository
) : ViewModel() {

    private val _searchResult = mutableStateOf<List<com.kanyideveloper.muviz.data.remote.responses.debug.Result>>(emptyList())
    val searchResult: State<List<Result>> = _searchResult

    var isLoading= mutableStateOf(false)

    var loadingError = mutableStateOf("")

/*    init {
        searchAll("The 100")
    }*/


/*    fun multiSearch(searchParam: String){
        viewModelScope.launch {
            when (val result = filmsRepository.multiSearch(searchParam)) {
                is Resource.Success -> {
                    Timber.d(result.data?.results.toString())
                    _searchResult.value = result.data?.results!!
                    isLoading.value = false
                }
                is Resource.Error -> {
                    loadingError.value = result.message.toString()
                    isLoading.value = false
                }
            }
        }
    }*/

    fun searchAll(searchParam: String){
        viewModelScope.launch {
            when(val result = filmsRepository.searchAll(searchParam)){
                is Resource.Success -> {
                    Timber.d("${result.data}")
                    _searchResult.value  = result.data?.results!!.filter {
                        ((it.title != null || it.originalName != null || it.originalTitle != null) &&
                                (it.mediaType == "tv" || it.mediaType == "movie"))}
                    isLoading.value = false
                }
                is Resource.Error -> {
                    Timber.d("${result.message}")
                    loadingError.value = result.message.toString()
                    isLoading.value = false
                }
            }
        }
    }
}