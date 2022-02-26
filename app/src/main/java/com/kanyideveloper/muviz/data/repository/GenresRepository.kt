package com.kanyideveloper.muviz.data.repository

import com.kanyideveloper.muviz.data.remote.TMDBApi
import com.kanyideveloper.muviz.data.remote.responses.Genre
import com.kanyideveloper.muviz.util.Resource
import timber.log.Timber
import javax.inject.Inject

/*
class GenresRepository @Inject constructor(private val api: TMDBApi) {
    suspend fun getMoviesGenres(language: String): Resource<List<Genre>>{
        val response = try {
            api.getMovieGenres()
        }catch (e: Exception){
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Response: $response")
        return Resource.Success(response)
    }
    //suspend fun getTvSeriesGenres(language: String): Genre
}*/
