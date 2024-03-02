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
package com.kanyideveloper.muviz.filmdetail.presentation

import com.kanyideveloper.muviz.cast.domain.model.Cast
import com.kanyideveloper.muviz.cast.domain.model.Credits
import com.kanyideveloper.muviz.favorites.data.data.local.Favorite

sealed interface FilmDetailsUiEvents {
    data object NavigateBack : FilmDetailsUiEvents
    data class NavigateToCastsScreen(val credits: Credits) :
        FilmDetailsUiEvents

    data class AddToFavorites(val favorite: Favorite) :
        FilmDetailsUiEvents

    data class RemoveFromFavorites(val favorite: Favorite) :
        FilmDetailsUiEvents
    data class NavigateToCastDetails(val cast: Cast) : FilmDetailsUiEvents
}
