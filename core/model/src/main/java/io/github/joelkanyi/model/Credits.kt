package io.github.joelkanyi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Credits(
    val cast: List<Cast>,
    val id: Int
) : Parcelable