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
package io.github.joelkanyi.favorites.di

import android.app.Application
import androidx.room.Room
import io.github.joelkanyi.favorites.data.data.repository.FavoritesRepositoryImpl
import io.github.joelkanyi.favorites.domain.repository.FavoritesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.joelkanyi.core.common.util.Constants.DATABASE_NAME
import io.github.joelkanyi.favorites.data.data.local.FavoritesDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoritesModule {
    @Binds
    abstract fun bindFavoritesRepository(
        favoritesRepositoryImpl: FavoritesRepositoryImpl
    ): FavoritesRepository

    companion object {
        @Provides
        @Singleton
        fun provideFavoritesDatabase(application: Application): FavoritesDatabase {
            return Room.databaseBuilder(
                application.applicationContext,
                FavoritesDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
        }
    }
}
