package io.github.joelkanyi.muviz.mobile.navigation

import com.ramcosta.composedestinations.generated.account.destinations.AboutScreenDestination
import com.ramcosta.composedestinations.generated.cast.destinations.CastDetailsScreenDestination
import com.ramcosta.composedestinations.generated.cast.destinations.CastsScreenDestination
import com.ramcosta.composedestinations.generated.film.destinations.FilmDetailsScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.joelkanyi.account.presentation.navigation.AccountNavigator
import io.github.joelkanyi.cast.presentation.navigation.CastNavigator
import io.github.joelkanyi.core.common.domain.model.Cast
import io.github.joelkanyi.core.common.domain.model.Credits
import io.github.joelkanyi.core.common.domain.model.Film
import io.github.joelkanyi.core.common.util.BaseNavigator
import io.github.joelkanyi.favorites.presentation.navigation.FavoritesNavigator
import io.github.joelkanyi.film.presentation.navigation.FilmNavigator
import io.github.joelkanyi.home.presentation.navigation.HomeNavigator
import io.github.joelkanyi.search.presentation.navigation.SearchNavigator

class MobileAppNavigator(
    private val navigator: DestinationsNavigator
) : BaseNavigator, HomeNavigator, AccountNavigator, CastNavigator,
    FavoritesNavigator, FilmNavigator, SearchNavigator {
    override fun navigateUp() {
        navigator.navigateUp()
    }

    override fun navigateToFilmDetails(film: Film) {
        navigator.navigate(
            FilmDetailsScreenDestination(
                film = film
            )
        )
    }

    override fun navigateToCastDetails(cast: Cast) {
        navigator.navigate(
            CastDetailsScreenDestination(
                cast = cast
            )
        )
    }

    override fun navigateToAbout() {
        navigator.navigate(
            AboutScreenDestination()
        )
    }

    override fun navigateCasts(credits: Credits) {
        navigator.navigate(
            CastsScreenDestination(
                credits = credits
            )
        )
    }
}