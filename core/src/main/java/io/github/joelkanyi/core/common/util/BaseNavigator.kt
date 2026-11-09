package io.github.joelkanyi.core.common.util

import io.github.joelkanyi.model.Cast
import io.github.joelkanyi.model.Film

interface BaseNavigator {
    fun navigateUp()
    fun navigateToFilmDetails(
        film: Film
    )
    fun navigateToCastDetails(cast: Cast)
}