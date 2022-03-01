package com.kanyideveloper.muviz.data.remote.responses


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Credits(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("id")
    val id: Int
): Parcelable