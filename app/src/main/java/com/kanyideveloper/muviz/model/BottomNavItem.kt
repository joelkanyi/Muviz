package com.kanyideveloper.muviz.model

import com.kanyideveloper.muviz.R
import com.kanyideveloper.muviz.screens.destinations.AccountScreenDestination
import com.kanyideveloper.muviz.screens.destinations.Destination
import com.kanyideveloper.muviz.screens.destinations.FavoritesScreenDestination
import com.kanyideveloper.muviz.screens.destinations.HomeScreenDestination


/*data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector
)*/

sealed class BottomNavItem(var title: String, var icon: Int, var destination: Destination) {
    object Home : BottomNavItem(
        title = "Home",
        icon = R.drawable.ic_home,
        destination = HomeScreenDestination
    )
    object Favorites: BottomNavItem(
        title = "Favorites",
        icon = R.drawable.ic_star,
        destination = FavoritesScreenDestination
    )
    object Account: BottomNavItem(
        title = "Account",
        icon = R.drawable.ic_profile,
        destination = AccountScreenDestination
    )
}