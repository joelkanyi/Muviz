package com.kanyideveloper.muviz.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class FilmType(
    val type: String,
    val filmId: Int
): Parcelable
