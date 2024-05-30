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
package com.kanyideveloper.muviz.filmdetail.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.kanyideveloper.muviz.R
import com.kanyideveloper.muviz.common.util.Constants
import com.kanyideveloper.muviz.filmdetail.presentation.FilmDetailsUiState

@Composable
fun FilmInfo(
    modifier: Modifier = Modifier,
    filmOverview: String,
    filmReleaseDate: String,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = filmOverview,
            style = MaterialTheme.typography.bodyMedium,
        )

        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) {
                    append(
                        stringResource(R.string.release_date)
                    )
                }
                append(": ")
                append(
                    filmReleaseDate
                )
            },
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
fun Genres(
    filmType: String,
    state: FilmDetailsUiState,
    modifier: Modifier = Modifier,
) {
    val genres =
        if (filmType == Constants.TYPE_MOVIE) state.movieDetails?.genres else state.tvSeriesDetails?.genres
    FlowRow(
        modifier = modifier,
    ) {
        if (genres != null) {
            for (genre in genres) {
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .background(
                            MaterialTheme.colorScheme.primary.copy(.2f),
                            MaterialTheme.shapes.small
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 2.dp),
                        text = genre.name,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
    }
}
