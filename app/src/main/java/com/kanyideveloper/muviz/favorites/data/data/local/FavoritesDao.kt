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
package com.kanyideveloper.muviz.favorites.data.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Insert
    suspend fun insertFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorites_table ORDER BY mediaId DESC")
    fun getAllFavorites(): Flow<List<Favorite>>

    @Query("SELECT * FROM favorites_table WHERE mediaId  == :mediaId")
    fun getAFavorites(mediaId: Int): Flow<Favorite?>

    @Query("SELECT favorite FROM favorites_table WHERE mediaId = :mediaId")
    fun isFavorite(mediaId: Int): Flow<Boolean>

    @Delete
    suspend fun deleteAFavorite(favorite: Favorite)

    @Query("DELETE FROM favorites_table")
    suspend fun deleteAllFavorites()

    @Delete
    suspend fun deleteFavorites(favorites: List<Favorite>)
}
