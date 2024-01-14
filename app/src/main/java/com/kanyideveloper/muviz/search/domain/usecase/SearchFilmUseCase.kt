/*
 * Copyright 2024 Joel Kanyi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kanyideveloper.muviz.search.domain.usecase

import androidx.paging.PagingData
import androidx.paging.filter
import com.kanyideveloper.muviz.search.domain.model.Search
import com.kanyideveloper.muviz.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchFilmUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    operator fun invoke(searchParam: String): Flow<PagingData<Search>> {
        return repository.multiSearch(searchParam).map { pagingData ->
            pagingData.filter { search ->
                ((search.title != null || search.originalName != null || search.originalTitle != null) &&
                        (search.mediaType == "tv" || search.mediaType == "movie"))
            }
        }
    }
}
