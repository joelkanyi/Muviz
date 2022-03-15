package com.kanyideveloper.muviz.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Favorite::class], version = 4)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract val dao: FavoritesDao
}