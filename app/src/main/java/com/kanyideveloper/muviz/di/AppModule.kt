package com.kanyideveloper.muviz.di

import com.kanyideveloper.muviz.data.remote.TMDBApi
import com.kanyideveloper.muviz.data.repository.FilmsRepository
import com.kanyideveloper.muviz.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesTMDBApi() : TMDBApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TMDBApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFilmsRepository(api: TMDBApi) = FilmsRepository(api)
}