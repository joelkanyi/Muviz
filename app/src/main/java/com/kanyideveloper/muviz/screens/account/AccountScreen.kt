package com.kanyideveloper.muviz.screens.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kanyideveloper.muviz.R
import com.kanyideveloper.muviz.model.AccountItem
import com.kanyideveloper.muviz.presentation.components.StandardToolbar
import com.kanyideveloper.muviz.ui.theme.primaryGray
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun AccountScreen(
    navigator: DestinationsNavigator
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        StandardToolbar(
            navigator = navigator,
            title = {
                Text(
                    text = "Account",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = false
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp)
        ) {

            val accountItems = listOf(
                AccountItem(
                    "Change theme",
                    R.drawable.ic_theme
                ),
                AccountItem(
                    "Language",
                    R.drawable.ic_star_filled
                ),
                AccountItem(
                    "About",
                    R.drawable.ic_danger_circle
                ),
                AccountItem(
                    "Rate us",
                    R.drawable.ic_star
                ),
                AccountItem(
                    "Share",
                    R.drawable.ic_share
                )
            )

            items(accountItems) { item ->
                AccountItems(accountItem = item)
            }

        }
    }
}

@Composable
fun AccountItems(
    accountItem: AccountItem
) {
    Column() {

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp),
                painter = painterResource(id = accountItem.icon),
                contentDescription = null,
                tint = primaryGray
            )

            Spacer(modifier = Modifier.width(16.dp))
            Text(text = accountItem.title, color = Color.White)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Divider(
            color = primaryGray,
            thickness = 1.dp,
            modifier = Modifier
                .padding(
                    start = 8.dp,
                    end = 8.dp
                )
        )

    }
}