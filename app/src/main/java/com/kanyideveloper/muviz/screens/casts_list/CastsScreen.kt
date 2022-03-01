package com.kanyideveloper.muviz.screens.casts_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.kanyideveloper.muviz.R
import com.kanyideveloper.muviz.data.remote.responses.Cast
import com.kanyideveloper.muviz.data.remote.responses.Credits
import com.kanyideveloper.muviz.presentation.components.StandardToolbar
import com.kanyideveloper.muviz.screens.commons.CastItem
import com.kanyideveloper.muviz.ui.theme.lightGray
import com.kanyideveloper.muviz.util.Constants
import com.kanyideveloper.muviz.util.Constants.IMAGE_BASE_URL
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import timber.log.Timber

@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun CastsScreen(
    credits: Credits,
    navigator: DestinationsNavigator
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        StandardToolbar(
            navigator = navigator,
            title = {
                Text(
                    text = "Casts",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = true
        )

        Timber.d(credits.cast[0].profilePath)

        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(credits.cast) { cast ->
                Timber.d(cast.toString())

                val imageLink = if (cast.profilePath.equals("") || cast.profilePath == null){
                    "https://pixy.org/src/9/94083.png"
                }else{
                    cast.profilePath
                }

                CastItem(
                    size = 170.dp,
                    castImageUrl = "${Constants.IMAGE_BASE_UR}/$imageLink",
                    castName = cast.name!!
                )
            }
        }
    }
}