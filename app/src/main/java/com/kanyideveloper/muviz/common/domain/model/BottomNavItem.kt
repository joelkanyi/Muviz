/*
 * Copyright 2024 Joel Kanyi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kanyideveloper.muviz.common.domain.model

import com.kanyideveloper.muviz.R
import com.ramcosta.composedestinations.generated.destinations.AccountScreenDestination
import com.ramcosta.composedestinations.generated.destinations.FavoritesScreenDestination
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SearchScreenDestination

sealed class BottomNavItem(
    val title: String,
    val icon: Int,
    val route: String,
) {
    data object Home : BottomNavItem(
        title = "Home",
        icon = R.drawable.ic_home,
        route = HomeScreenDestination.route
    )
    data object Search: BottomNavItem(
        title = "Search",
        icon = R.drawable.ic_search,
        route = SearchScreenDestination.route,
    )
    data object Favorites: BottomNavItem(
        title = "Favorites",
        icon = R.drawable.ic_star,
        route = FavoritesScreenDestination.route
    )
    data object Account: BottomNavItem(
        title = "Account",
        icon = R.drawable.ic_profile,
        route = AccountScreenDestination.route
    )
}
