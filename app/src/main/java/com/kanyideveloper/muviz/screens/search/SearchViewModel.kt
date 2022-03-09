package com.kanyideveloper.muviz.screens.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.kanyideveloper.muviz.data.remote.responses.Search
import com.kanyideveloper.muviz.data.repository.FilmsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val filmsRepository: FilmsRepository
) : ViewModel() {

    private val _searchResult = mutableStateOf<Flow<PagingData<Search>>>(emptyFlow())
    val searchSearch: State<Flow<PagingData<Search>>> = _searchResult

    fun searchAll(searchParam: String) {
        viewModelScope.launch {
            _searchResult.value = filmsRepository.multiSearch(searchParam).map { pagingData ->
                pagingData.filter {
                    ((it.title != null || it.originalName != null || it.originalTitle != null) &&
                            (it.mediaType == "tv" || it.mediaType == "movie"))
                }
            }.cachedIn(viewModelScope)
        }
    }
}