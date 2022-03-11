package com.kanyideveloper.muviz.data.remote.responses


import com.google.gson.annotations.SerializedName
import com.kanyideveloper.muviz.model.Search

data class MultiSearchResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val searches: List<Search>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)