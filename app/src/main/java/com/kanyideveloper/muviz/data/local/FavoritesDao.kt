package com.kanyideveloper.muviz.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoritesDao {

    @Insert
    suspend fun insertFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorites_table ORDER BY mediaId DESC")
    fun getAllFavorites(): LiveData<List<Favorite>>

    @Query("SELECT * FROM favorites_table WHERE mediaId  == :mediaId")
    fun getAFavorites(mediaId: Int): LiveData<Favorite?>

    @Query("SELECT favorite FROM favorites_table WHERE mediaId = :mediaId")
    fun isFavorite(mediaId: Int): LiveData<Boolean>

    @Delete
    suspend fun deleteAFavorite(favorite: Favorite)

    @Query("DELETE FROM favorites_table")
    suspend fun deleteAllFavorites()
}