package com.kanyideveloper.muviz.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Film(
    val name: String,
    val releaseDate: String
): Parcelable
