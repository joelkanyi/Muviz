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
package com.kanyideveloper.muviz.cast.data.repository

import com.kanyideveloper.muviz.cast.domain.model.Credits
import com.kanyideveloper.muviz.cast.domain.repository.CastRepository
import com.kanyideveloper.muviz.common.data.network.TMDBApi
import com.kanyideveloper.muviz.common.util.Resource
import com.kanyideveloper.muviz.filmdetail.data.mappers.toDomain
import timber.log.Timber
import javax.inject.Inject

class CastRepositoryImpl @Inject constructor(
    private val api: TMDBApi,
) : CastRepository {
    // Series Casts
    override suspend fun getTvSeriesCasts(id: Int): Resource<Credits> {
        val response = try {
            api.getTvSeriesCredits(id)
        } catch (e: Exception) {
            Timber.d(e.message)
            return Resource.Error("Unknown error occurred")
        }

        Timber.d("Series cast $response")
        return Resource.Success(response.toDomain())
    }

    // Movie Casts
    override suspend fun getMovieCasts(id: Int): Resource<Credits> {
        val response = try {
            api.getMovieCredits(id)
        } catch (e: Exception) {
            Timber.d(e.message)
            return Resource.Error("Unknown error occurred")
        }

        Timber.d("Movie Casts $response")
        return Resource.Success(response.toDomain())
    }
}
