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
package com.kanyideveloper.muviz.favorites.data.data.repository

import com.kanyideveloper.muviz.favorites.data.data.local.Favorite
import com.kanyideveloper.muviz.favorites.data.data.local.FavoritesDatabase
import com.kanyideveloper.muviz.favorites.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(private val database: FavoritesDatabase):
    FavoritesRepository {
    override suspend fun insertFavorite(favorite: Favorite) {
        database.dao.insertFavorite(favorite)
    }

    override fun getFavorites(): Flow<List<Favorite>> {
        return database.dao.getAllFavorites()
    }

    override fun isFavorite(mediaId: Int): Flow<Boolean>{
        return database.dao.isFavorite(mediaId)
    }

    override fun getAFavorites(mediaId: Int): Flow<Favorite?> {
        return database.dao.getAFavorites(mediaId)
    }

    override suspend fun deleteOneFavorite(favorite: Favorite) {
        database.dao.deleteAFavorite(favorite)
    }

    override suspend fun deleteAllFavorites() {
        database.dao.deleteAllFavorites()
    }

    override suspend fun deleteFavorites(favorites: List<Favorite>) {
        database.dao.deleteFavorites(favorites)
    }
}
