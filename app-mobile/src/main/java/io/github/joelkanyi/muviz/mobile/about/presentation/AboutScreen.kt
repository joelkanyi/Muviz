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
package io.github.joelkanyi.muviz.mobile.about.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.joelkanyi.core.ui.components.StandardToolbar
import io.github.joelkanyi.core.ui.theme.MuvizTheme
import io.github.joelkanyi.core.common.util.appVersionCode
import io.github.joelkanyi.core.common.util.appVersionName
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.joelkanyi.muviz.mobile.R

@Destination<RootGraph>
@Composable
fun AboutScreen(
    navigator: DestinationsNavigator
) {
    AboutScreenContent(
        events = { event ->
            when (event) {
                is AboutScreenUiEvents.NavigateBack -> {
                    navigator.popBackStack()
                }
            }
        }
    )
}

@Composable
fun AboutScreenContent(
    events: (AboutScreenUiEvents) -> Unit,
) {
    Scaffold(
        topBar = {
            StandardToolbar(
                title = {
                    Text(
                        text = stringResource(R.string.about_title),
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                onBackArrowClicked = {
                    events(AboutScreenUiEvents.NavigateBack)
                },
                modifier = Modifier.fillMaxWidth(),
                showBackArrow = true
            )
        }
    ) { innerPadding ->
        val context = LocalContext.current

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
        ) {
            item {
                Image(
                    painterResource(
                        id = R.drawable.muviz
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .padding(8.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = stringResource(R.string.about),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                )
            }

            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(
                        R.string.v,
                        context.appVersionName(),
                        context.appVersionCode()
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview
@Composable
fun AboutScreenPreview() {
    MuvizTheme {
        AboutScreenContent(
            events = {}
        )
    }
}
