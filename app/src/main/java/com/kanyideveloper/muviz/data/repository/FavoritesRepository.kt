package com.kanyideveloper.muviz.data.repository

import androidx.lifecycle.LiveData
import com.kanyideveloper.muviz.data.local.Favorite
import com.kanyideveloper.muviz.data.local.FavoritesDatabase
import javax.inject.Inject

class FavoritesRepository @Inject constructor(private val database: FavoritesDatabase) {
    suspend fun insertFavorite(favorite: Favorite) {
        database.dao.insertFavorite(favorite)
    }

    fun getFavorites(): LiveData<List<Favorite>> {
        return database.dao.getAllFavorites()
    }

    fun isFavorite(mediaId: Int): LiveData<Boolean>{
        return database.dao.isFavorite(mediaId)
    }

    fun getAFavorites(mediaId: Int): LiveData<Favorite?> {
        return database.dao.getAFavorites(mediaId)
    }

    suspend fun deleteOneFavorite(favorite: Favorite) {
        database.dao.deleteAFavorite(favorite)
    }

    suspend fun deleteAllFavorites() {
        database.dao.deleteAllFavorites()
    }
}