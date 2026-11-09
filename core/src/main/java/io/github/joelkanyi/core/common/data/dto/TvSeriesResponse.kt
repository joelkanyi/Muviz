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
package io.github.joelkanyi.core.common.data.dto

import com.google.gson.annotations.SerializedName
import io.github.joelkanyi.model.Series

data class TvSeriesResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<SeriesResponse>,
    @SerializedName("total_pages")
    val total_pages: Int,
    @SerializedName("total_results")
    val total_results: Int
) {
    companion object {
        fun SeriesResponse.toDomainModel() = Series(
            backdropPath = backdropPath ?: "",
            firstAirDate = firstAirDate ?: "",
            genreIds = genreIds ?: emptyList(),
            id = id,
            name = name ?: "",
            originCountry = originCountry ?: emptyList(),
            originalLanguage = originalLanguage ?: "",
            originalName = originalName ?: "",
            overview = overview ?: "",
            popularity = popularity ?: 0.0,
            posterPath = posterPath ?: "",
            voteAverage = voteAverage ?: 0.0,
            voteCount = voteCount ?: 0
        )
    }
}
