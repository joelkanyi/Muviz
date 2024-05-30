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
package com.kanyideveloper.muviz.home.presentation

import com.kanyideveloper.muviz.common.domain.model.Film
import com.kanyideveloper.muviz.genre.domain.model.Genre

sealed interface HomeUiEvents {
    data object OnSearchClick : HomeUiEvents
    data object NavigateBack : HomeUiEvents
    data object OnPullToRefresh : HomeUiEvents

    data class NavigateToFilmDetails(
        val film: Film,
    ) : HomeUiEvents

    data class OnFilmGenreSelected(
        val genre: Genre,
        val filmType: String,
        val selectedFilmOption: String
    ) :
        HomeUiEvents

    data class OnFilmOptionSelected(val item: String) :
        HomeUiEvents
}
