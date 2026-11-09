package io.github.joelkanyi.muviz.mobile.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.navgraphs.MobileAppGraph
import com.ramcosta.composedestinations.navigation.dependency

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MobileAppNavHost(
    innerPadding: PaddingValues,
    navController: NavHostController,
    mobileAppNavigator: MobileAppNavigator
) {
    SharedTransitionLayout(
        modifier = Modifier
            .consumeWindowInsets(innerPadding)
            .padding(innerPadding),
    ) {
        DestinationsNavHost(
            navGraph = MobileAppGraph,
            navController = navController,
            dependenciesContainerBuilder = {
                dependency(mobileAppNavigator)
                dependency(this@SharedTransitionLayout)
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
