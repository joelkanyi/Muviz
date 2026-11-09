package io.github.joelkanyi.muviz.mobile.navigation

import com.ramcosta.composedestinations.annotation.ExternalNavGraph
import com.ramcosta.composedestinations.annotation.NavHostGraph
import com.ramcosta.composedestinations.generated.account.navgraphs.AccountGraph
import com.ramcosta.composedestinations.generated.cast.navgraphs.CastGraph
import com.ramcosta.composedestinations.generated.favorites.navgraphs.FavoritesGraph
import com.ramcosta.composedestinations.generated.film.navgraphs.FilmGraph
import com.ramcosta.composedestinations.generated.home.navgraphs.HomeGraph
import com.ramcosta.composedestinations.generated.search.navgraphs.SearchGraph

@NavHostGraph
annotation class MobileAppNavGraph {
    @ExternalNavGraph<HomeGraph>(start = true)
    @ExternalNavGraph<AccountGraph>
    @ExternalNavGraph<SearchGraph>
    @ExternalNavGraph<CastGraph>
    @ExternalNavGraph<FilmGraph>
    @ExternalNavGraph<FavoritesGraph>
    companion object Includes
}