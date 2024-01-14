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
package com.kanyideveloper.muviz.genre.data.repository

import com.kanyideveloper.muviz.common.data.network.TMDBApi
import com.kanyideveloper.muviz.common.util.Resource
import com.kanyideveloper.muviz.genre.domain.model.Genre
import com.kanyideveloper.muviz.genre.domain.repository.GenreRepository
import com.kanyideveloper.muviz.genre.data.mappers.toDomain
import timber.log.Timber
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val api: TMDBApi,
) : GenreRepository {
    override suspend fun getMovieGenres(): Resource<List<Genre>> {
        val response = try {
            api.getMovieGenres()
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Movies genres: $response")
        return Resource.Success(
            response.genres.map { it.toDomain() }
        )
    }

    override suspend fun getTvSeriesGenres(): Resource<List<Genre>> {
        val response = try {
            api.getTvSeriesGenres()
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Series genres: $response")
        return Resource.Success(
            response.genres.map { it.toDomain() }
        )
    }
}
