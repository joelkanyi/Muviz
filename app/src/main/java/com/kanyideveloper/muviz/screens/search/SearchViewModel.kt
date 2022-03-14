package com.kanyideveloper.muviz.screens.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.kanyideveloper.muviz.data.repository.SearchRepository
import com.kanyideveloper.muviz.model.Search
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {

    private val _searchTerm = mutableStateOf("")
    val searchTerm: State<String> = _searchTerm

    fun setSearchTerm(term: String){
        _searchTerm.value = term
    }

    private val _searchResult = mutableStateOf<Flow<PagingData<Search>>>(emptyFlow())
    val searchSearch: State<Flow<PagingData<Search>>> = _searchResult

    fun searchAll(searchParam: String) {
        viewModelScope.launch {
            _searchResult.value = repository.multiSearch(searchParam).map { pagingData ->
                pagingData.filter {
                    ((it.title != null || it.originalName != null || it.originalTitle != null) &&
                            (it.mediaType == "tv" || it.mediaType == "movie"))
                }
            }.cachedIn(viewModelScope)
        }
    }
}