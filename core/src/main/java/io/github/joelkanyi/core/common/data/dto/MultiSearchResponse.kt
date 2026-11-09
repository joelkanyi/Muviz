package io.github.joelkanyi.core.common.data.dto

import com.google.gson.annotations.SerializedName

data class MultiSearchResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val searches: List<SearchResponse>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)