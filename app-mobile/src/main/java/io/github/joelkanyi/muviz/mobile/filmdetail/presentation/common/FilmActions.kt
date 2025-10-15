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
package io.github.joelkanyi.muviz.mobile.filmdetail.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import io.github.joelkanyi.muviz.mobile.common.domain.model.Film
import io.github.joelkanyi.core.ui.components.CircleButton
import io.github.joelkanyi.core.common.util.createImageUrl
import io.github.joelkanyi.muviz.mobile.R
import io.github.joelkanyi.muviz.mobile.favorites.data.data.local.Favorite
import io.github.joelkanyi.muviz.mobile.filmdetail.presentation.FilmDetailsUiEvents

@Composable
fun FilmActions(
    modifier: Modifier = Modifier,
    onEvents: (FilmDetailsUiEvents) -> Unit,
    isLiked: Boolean,
    film: Film,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        CircleButton(
            onClick = {
                onEvents(FilmDetailsUiEvents.NavigateBack)
            },
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_left),
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = null
            )
        }

        CircleButton(
            onClick = {
                if (isLiked) {
                    onEvents(
                        FilmDetailsUiEvents.RemoveFromFavorites(
                            Favorite(
                                favorite = false,
                                mediaId = film.id,
                                mediaType = film.type,
                                image = film.image.createImageUrl(),
                                title = film.name,
                                releaseDate = film.releaseDate,
                                rating = film.rating,
                                overview = film.overview,
                            )
                        )
                    )
                } else {
                    onEvents(
                        FilmDetailsUiEvents.AddToFavorites(
                            Favorite(
                                favorite = true,
                                mediaId = film.id,
                                mediaType = film.type,
                                image = film.image.createImageUrl(),
                                title = film.name,
                                releaseDate = film.releaseDate,
                                rating = film.rating,
                                overview = film.overview,
                            )
                        )
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                tint = if (isLiked) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.background
                },
                contentDescription = if (isLiked) {
                    stringResource(id = R.string.unlike)
                } else {
                    stringResource(id = R.string.like)
                }
            )
        }
    }
}
