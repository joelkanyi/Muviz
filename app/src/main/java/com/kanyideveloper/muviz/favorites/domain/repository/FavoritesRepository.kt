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
package com.kanyideveloper.muviz.favorites.domain.repository

import com.kanyideveloper.muviz.favorites.data.data.local.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    suspend fun insertFavorite(favorite: Favorite)
    fun getFavorites(): Flow<List<Favorite>>
    fun isFavorite(mediaId: Int): Flow<Boolean>
    fun getAFavorites(mediaId: Int): Flow<Favorite?>
    suspend fun deleteOneFavorite(favorite: Favorite)
    suspend fun deleteAllFavorites()
    suspend fun deleteFavorites(favorites: List<Favorite>)
}
