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

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.kanyideveloper.muviz.R
import com.kanyideveloper.muviz.about.domain.model.AccountItem
import com.kanyideveloper.muviz.common.presentation.theme.MuvizTheme
import com.kanyideveloper.muviz.common.presentation.theme.primaryDark
import com.kanyideveloper.muviz.common.presentation.theme.primaryDarkVariant
import com.kanyideveloper.muviz.common.presentation.theme.primaryGray
import com.kanyideveloper.muviz.common.presentation.theme.primaryPink
import com.kanyideveloper.muviz.destinations.AboutScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun AccountScreen(
    navigator: DestinationsNavigator
) {
    var showSocialsDialog by rememberSaveable { mutableStateOf(false) }
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
}

@Composable
fun AccountScreenContent(
    onEvent: (AccountScreenUiEvents) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = primaryDark,
            ) {
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
            }
        }
    ) { innerPadding ->
        val context = LocalContext.current

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(accountItems) { item ->
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
                        }
                    }
                )
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
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Get in touch",
                color = Color.White,
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
                            tint = Color(0xFF0072ba),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Linkedin",
                            color = Color.White,
                            textAlign = TextAlign.Left,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chevron_right),
                        tint = primaryGray,
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
                            tint = Color(0xFF0072ba),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Twitter",
                            color = Color.White,
                            textAlign = TextAlign.Left,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chevron_right),
                        tint = primaryGray,
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
                            tint = Color(0xFF0072ba),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Github",
                            color = Color.White,
                            textAlign = TextAlign.Left,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chevron_right),
                        tint = primaryGray,
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
                            tint = Color(0xFF0072ba),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Facebook",
                            color = Color.White,
                            textAlign = TextAlign.Left,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chevron_right),
                        tint = primaryGray,
                        contentDescription = null
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(primaryPink)
            ) {
                Text(text = "Okay", color = Color.White)
            }
        },
        backgroundColor = primaryDarkVariant,
        contentColor = Color.Black,
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

private val accountItems = listOf(
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
    ),
    AccountItem(
        "Get in touch",
        R.drawable.ic_socials
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
