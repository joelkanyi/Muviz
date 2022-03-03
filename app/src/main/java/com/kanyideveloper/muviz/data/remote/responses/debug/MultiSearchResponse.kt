package com.kanyideveloper.muviz.data.remote.responses.debug


import com.google.gson.annotations.SerializedName

data class MultiSearchResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)