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

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kanyideveloper.muviz.R

@Composable
fun FilmImageBanner(
    modifier: Modifier = Modifier,
    filmImage: String,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(filmImage)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.ic_placeholder),
        contentDescription = "Movie Banner",
        contentScale = ContentScale.Crop,
        modifier = modifier.fillMaxSize(),
    )
}
