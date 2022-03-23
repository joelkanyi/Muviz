package com.kanyideveloper.muviz.screens.film_details.common

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.kanyideveloper.muviz.R
import com.kanyideveloper.muviz.data.local.Favorite
import com.kanyideveloper.muviz.screens.favorites.FavoritesViewModel
import com.kanyideveloper.muviz.ui.theme.AppBarCollapsedHeight
import com.kanyideveloper.muviz.ui.theme.AppBarExpendedHeight
import com.kanyideveloper.muviz.ui.theme.Transparent
import com.kanyideveloper.muviz.ui.theme.primaryDark
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlin.math.max
import kotlin.math.min

@Composable
fun FilmImageBanner(
    scrollState: LazyListState,
    posterUrl: String,
    filmName: String,
    filmId: Int,
    filmType: String,
    releaseDate: String,
    rating: Float,
    navigator: DestinationsNavigator,
    viewModel: FavoritesViewModel
) {

    val context = LocalContext.current

    val imageHeight = AppBarExpendedHeight - AppBarCollapsedHeight

    val maxOffset = with(LocalDensity.current) {
        imageHeight.roundToPx()
    } - LocalWindowInsets.current.systemBars.layoutInsets.top

    val offset = min(scrollState.firstVisibleItemScrollOffset, maxOffset)

    val offsetProgress = max(0f, offset * 3f - 2f * maxOffset) / maxOffset

    TopAppBar(
        contentPadding = PaddingValues(),
        backgroundColor = primaryDark,
        modifier = Modifier
            .height(
                AppBarExpendedHeight
            )
            .offset { IntOffset(x = 0, y = -offset) },
        elevation = if (offset == maxOffset) 4.dp else 0.dp
    ) {
        Column {
            Box {
                Image(
                    painter = rememberImagePainter(
                        data = posterUrl,
                        builder = {
                            placeholder(R.drawable.ic_placeholder)
                            crossfade(true)
                        }
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .height(imageHeight)
                        .graphicsLayer {
                            alpha = 1f - offsetProgress
                        },
                    contentScale = ContentScale.Crop,
                    contentDescription = "Movie Banner"
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colorStops = arrayOf(
                                    Pair(0.3f, Transparent),
                                    Pair(1.5f, primaryDark)
                                )
                            )
                        )
                )
                FilmNameAndRating(
                    filmName = filmName,
                    rating = rating
                )
            }
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 10.dp)
    ) {
        CircularBackButtons(
            onClick = {
                navigator.popBackStack()
            }
        )


        CircularFavoriteButtons(
            isLiked = viewModel.isAFavorite(filmId).observeAsState().value != null,
            onClick = { isFav ->
                if (isFav) {
                    Toast.makeText(context, "Already added to your favorites", Toast.LENGTH_SHORT).show()
                    return@CircularFavoriteButtons
                } else {
                    viewModel.insertFavorite(
                        Favorite(
                            favorite = true,
                            mediaId = filmId,
                            mediaType = filmType,
                            image = posterUrl,
                            title = filmName,
                            releaseDate = releaseDate,
                            rating = rating
                        )
                    )
                }
            }
        )
    }
}