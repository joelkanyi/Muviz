package com.kanyideveloper.muviz.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kanyideveloper.muviz.data.paging.SearchPagingSource
import com.kanyideveloper.muviz.data.remote.TMDBApi
import com.kanyideveloper.muviz.model.Search
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepository @Inject constructor(private val api: TMDBApi) {
    fun multiSearch(queryParam: String): Flow<PagingData<Search>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 27),
            pagingSourceFactory = {
                SearchPagingSource(api, queryParam)
            }
        ).flow
    }
}