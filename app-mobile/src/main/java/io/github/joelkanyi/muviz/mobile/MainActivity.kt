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
package io.github.joelkanyi.muviz.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.generated.account.destinations.AccountScreenDestination
import com.ramcosta.composedestinations.generated.favorites.destinations.FavoritesScreenDestination
import com.ramcosta.composedestinations.generated.home.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.generated.search.destinations.SearchScreenDestination
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.utils.rememberDestinationsNavigator
import dagger.hilt.android.AndroidEntryPoint
import io.github.joelkanyi.core.ui.theme.MuvizTheme
import io.github.joelkanyi.core.ui.theme.Theme
import io.github.joelkanyi.muviz.mobile.navigation.MobileAppNavHost
import io.github.joelkanyi.muviz.mobile.navigation.MobileAppNavigator
import kotlinx.coroutines.Dispatchers

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = hiltViewModel<MainViewModel>()
            val themeValue by viewModel.theme.collectAsState(
                initial = Theme.FOLLOW_SYSTEM.themeValue,
                context = Dispatchers.Main.immediate
            )

            MuvizTheme(themeValue) {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val destinationsNavigator = navController.rememberDestinationsNavigator()
                val mobileAppNavigator = remember(destinationsNavigator) {
                    MobileAppNavigator(destinationsNavigator)
                }

                val bottomNavItems = listOf(
                    BottomNavItem.Home,
                    BottomNavItem.Search,
                    BottomNavItem.Favorites,
                    BottomNavItem.Account
                )

                val showBottomBar = navBackStackEntry?.destination?.route in
                        bottomNavItems.map { it.direction.route }

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            NavigationBar(
                                containerColor = MaterialTheme.colorScheme.background,
                                contentColor = MaterialTheme.colorScheme.onBackground,
                            ) {
                                val currentDestination = navBackStackEntry?.destination
                                bottomNavItems.forEach { item ->
                                    NavigationBarItem(
                                        icon = {
                                            Icon(
                                                modifier = Modifier.size(24.dp),
                                                painter = painterResource(id = item.icon),
                                                contentDescription = item.title
                                            )
                                        },
                                        label = {
                                            Text(
                                                text = item.title,
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        },
                                        selected = currentDestination?.route?.contains(item.direction.route) == true,
                                        onClick = {
                                            destinationsNavigator.navigate(item.direction) {
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                ) { paddingValues ->
                    MobileAppNavHost(
                        innerPadding = paddingValues,
                        navController = navController,
                        mobileAppNavigator = mobileAppNavigator
                    )
                }
            }
        }

    }
}

sealed class BottomNavItem(
    val title: String,
    val icon: Int,
    val direction: Direction,
) {
    data object Home : BottomNavItem(
        title = "Home",
        icon = R.drawable.ic_home,
        direction = HomeScreenDestination
    )

    data object Search : BottomNavItem(
        title = "Search",
        icon = R.drawable.ic_search,
        direction = SearchScreenDestination,
    )

    data object Favorites : BottomNavItem(
        title = "Favorites",
        icon = R.drawable.ic_star,
        direction = FavoritesScreenDestination
    )

    data object Account : BottomNavItem(
        title = "Account",
        icon = R.drawable.ic_profile,
        direction = AccountScreenDestination
    )
}
