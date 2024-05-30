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
package com.kanyideveloper.muviz.account.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.kanyideveloper.muviz.R
import com.kanyideveloper.muviz.about.domain.model.AccountItem
import com.kanyideveloper.muviz.common.presentation.components.StandardToolbar
import com.kanyideveloper.muviz.common.presentation.theme.MuvizTheme
import com.kanyideveloper.muviz.common.presentation.theme.Theme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.AboutScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun AccountScreen(
    navigator: DestinationsNavigator,
    viewModel: AccountViewModel = hiltViewModel(),
) {
    var showSocialsDialog by rememberSaveable { mutableStateOf(false) }
    var shouldShowThemesDialog by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    AccountScreenContent(
        onEvent = { event ->
            when (event) {
                AccountScreenUiEvents.NavigateToAbout -> {
                    navigator.navigate(AboutScreenDestination)
                }

                AccountScreenUiEvents.OpenSocialsDialog -> {
                    showSocialsDialog = true
                }

                AccountScreenUiEvents.OnRateUsClicked -> {
                    try {
                        val rateIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + context.packageName)
                        )
                        startActivity(context, rateIntent, null)
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            "Unable to open the PlayStore",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                AccountScreenUiEvents.OnShareClicked -> {
                    try {
                        val appPackageName = context.packageName
                        val sendIntent = Intent()
                        sendIntent.action = Intent.ACTION_SEND
                        sendIntent.putExtra(
                            Intent.EXTRA_TEXT,
                            "Check out Muviz App on Playstore: https://play.google.com/store/apps/details?id=$appPackageName"
                        )
                        sendIntent.type = "text/plain"
                        context.startActivity(sendIntent)
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            "Unable to share the app",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                AccountScreenUiEvents.ShowThemesDialog -> {
                    shouldShowThemesDialog = true
                }
            }
        }
    )

    if (showSocialsDialog) {
        GetInTouchDialog(
            onDismiss = {
                showSocialsDialog = false
            }
        )
    }

    if (shouldShowThemesDialog) {
        ThemesDialog(
            selectedTheme = viewModel.theme.collectAsState().value,
            onDismiss = {
                shouldShowThemesDialog = false
            },
            onSelectTheme = {
                shouldShowThemesDialog = false
                viewModel.updateTheme(it)
            }
        )
    }
}

@Composable
fun AccountScreenContent(
    onEvent: (AccountScreenUiEvents) -> Unit
) {
    Scaffold(
        topBar = {
            StandardToolbar(
                title = {
                    Image(
                        painterResource(
                            id = R.drawable.muviz
                        ),
                        contentDescription = "App logo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .padding(8.dp)
                    )
                },
            )
        }
    ) { innerPadding ->
        val context = LocalContext.current

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(context.accountItems()) { index, item ->
                AccountItems(
                    accountItem = item,
                    onClick = {
                        when (item.title) {
                            context.getString(R.string.about_title) -> {
                                onEvent(
                                    AccountScreenUiEvents.NavigateToAbout
                                )
                            }

                            context.getString(R.string.rate_us) -> {
                                onEvent(
                                    AccountScreenUiEvents.OnRateUsClicked
                                )
                            }

                            context.getString(R.string.share) -> {
                                onEvent(
                                    AccountScreenUiEvents.OnShareClicked
                                )
                            }

                            context.getString(R.string.get_in_touch) -> {
                                onEvent(
                                    AccountScreenUiEvents.OpenSocialsDialog
                                )
                            }

                            context.getString(R.string.change_theme) -> {
                                onEvent(
                                    AccountScreenUiEvents.ShowThemesDialog
                                )
                            }
                        }
                    }
                )

                if (index != context.accountItems().size - 1) {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onBackground.copy(.5f),
                        thickness = .5.dp,
                    )
                }
            }
        }
    }
}

@Composable
private fun GetInTouchDialog(
    onDismiss: () -> Unit,
) {
    val context = LocalContext.current
    AlertDialog(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        onDismissRequest = onDismiss,
        tonalElevation = 0.dp,
        containerColor = MaterialTheme.colorScheme.background,
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Get in touch",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data =
                                Uri.parse("https://www.linkedin.com/in/joel-kanyi-037270174/")
                            startActivity(context, intent, null)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic__linkedin),
                            tint = MaterialTheme.colorScheme.onBackground.copy(.5f),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Linkedin",
                            textAlign = TextAlign.Left,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chevron_right),
                        tint = MaterialTheme.colorScheme.onBackground.copy(.5f),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            Toast
                                .makeText(context, "Twitter", Toast.LENGTH_SHORT)
                                .show()

                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data =
                                Uri.parse("https://twitter.com/_joelkanyi")
                            startActivity(context, intent, null)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_twitter),
                            tint = MaterialTheme.colorScheme.onBackground.copy(.5f),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Twitter",
                            textAlign = TextAlign.Left,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chevron_right),
                        tint = MaterialTheme.colorScheme.onBackground.copy(.5f),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            Toast
                                .makeText(context, "Github", Toast.LENGTH_SHORT)
                                .show()

                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data =
                                Uri.parse("https://github.com/JoelKanyi")
                            startActivity(context, intent, null)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_github),
                            tint = MaterialTheme.colorScheme.onBackground.copy(.5f),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Github",
                            textAlign = TextAlign.Left,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chevron_right),
                        tint = MaterialTheme.colorScheme.onBackground.copy(.5f),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            Toast
                                .makeText(context, "Facebook", Toast.LENGTH_SHORT)
                                .show()

                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data =
                                Uri.parse("https://www.facebook.com/joel.kanyi.71/")
                            startActivity(context, intent, null)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic__facebook),
                            tint = MaterialTheme.colorScheme.onBackground.copy(.5f),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Facebook",
                            textAlign = TextAlign.Left,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chevron_right),
                        tint = MaterialTheme.colorScheme.onBackground.copy(.5f),
                        contentDescription = null
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
            ) {
                Text(
                    text = "Okay",
                )
            }
        },
        shape = RoundedCornerShape(10.dp)
    )
}

@Composable
fun AccountItems(
    accountItem: AccountItem,
    onClick: () -> Unit = {},
) {
    Column(modifier = Modifier
        .clickable {
            onClick()
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter = painterResource(id = accountItem.icon),
                contentDescription = accountItem.title,
                tint = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = accountItem.title,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
fun ThemesDialog(
    onDismiss: () -> Unit,
    onSelectTheme: (Int) -> Unit,
    selectedTheme: Int,
) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = "Themes",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(themes) { theme ->
                    ThemeItem(
                        themeName = theme.themeName,
                        themeValue = theme.themeValue,
                        icon = theme.icon,
                        onSelectTheme = onSelectTheme,
                        isSelected = theme.themeValue == selectedTheme
                    )
                }
            }
        },
        confirmButton = {
            Text(
                text = "OK",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { onDismiss() }
            )
        }
    )
}

@Composable
fun ThemeItem(
    themeName: String,
    themeValue: Int,
    icon: Int,
    onSelectTheme: (Int) -> Unit,
    isSelected: Boolean,
) {
    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        onClick = {
            onSelectTheme(themeValue)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(.75f),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier
                        .padding(12.dp),
                    text = themeName,
                    style = MaterialTheme.typography.labelMedium
                )
            }

            if (isSelected) {
                Icon(
                    modifier = Modifier
                        .size(16.dp),
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                )
            }
        }
    }
}

private fun Context.accountItems() = listOf(
    AccountItem(
        getString(R.string.change_theme),
        R.drawable.ic_theme
    ),
    AccountItem(
        getString(R.string.about_title),
        R.drawable.ic_danger_circle
    ),
    AccountItem(
        getString(R.string.rate_us),
        R.drawable.ic_star
    ),
    AccountItem(
        getString(R.string.share),
        R.drawable.ic_share
    ),
    AccountItem(
        getString(R.string.get_in_touch),
        R.drawable.ic_socials
    )
)

data class AppTheme(
    val themeName: String,
    val themeValue: Int,
    val icon: Int
)

val themes = listOf(
    AppTheme(
        themeName = "Use System Settings",
        themeValue = Theme.FOLLOW_SYSTEM.themeValue,
        icon = R.drawable.settings_suggest
    ),
    AppTheme(
        themeName = "Light Mode",
        themeValue = Theme.LIGHT_THEME.themeValue,
        icon = R.drawable.light_mode
    ),
    AppTheme(
        themeName = "Dark Mode",
        themeValue = Theme.DARK_THEME.themeValue,
        icon = R.drawable.dark_mode
    ),
    AppTheme(
        themeName = "Material You",
        themeValue = Theme.MATERIAL_YOU.themeValue,
        icon = R.drawable.wallpaper
    )
)

@Preview
@Composable
fun AccountScreenPreview() {
    MuvizTheme {
        AccountScreenContent(
            onEvent = {}
        )
    }
}
