package io.github.joelkanyi.film.presentation.navigation

import io.github.joelkanyi.core.common.util.BaseNavigator
import io.github.joelkanyi.model.Credits

interface FilmNavigator: BaseNavigator {
    fun navigateCasts(credits: Credits)
}