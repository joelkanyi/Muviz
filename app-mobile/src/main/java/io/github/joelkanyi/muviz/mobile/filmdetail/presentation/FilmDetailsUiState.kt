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
package io.github.joelkanyi.muviz.mobile.filmdetail.presentation

import io.github.joelkanyi.muviz.mobile.cast.domain.model.Credits
import io.github.joelkanyi.muviz.mobile.home.data.network.dto.MovieDetails
import io.github.joelkanyi.muviz.mobile.home.data.network.dto.TvSeriesDetails

data class FilmDetailsUiState(
    val credits: Credits? = null,
    val isLoading: Boolean = false,
    val isLoadingCasts: Boolean = false,
    val error: String? = null,
    val errorCasts: String? = null,
    val tvSeriesDetails: TvSeriesDetails? = null,
    val movieDetails: MovieDetails? = null
)
