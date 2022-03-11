package com.kanyideveloper.muviz.data.remote.responses

import com.google.gson.annotations.SerializedName
import com.kanyideveloper.muviz.model.Genre

data class GenresResponse(
    @SerializedName("genres")
    val genres: List<Genre>
)