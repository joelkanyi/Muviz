package com.kanyideveloper.muviz.screens.casts_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
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
import com.kanyideveloper.muviz.R
import com.kanyideveloper.muviz.model.Cast
import com.kanyideveloper.muviz.presentation.components.StandardToolbar
import com.kanyideveloper.muviz.ui.theme.lightGray
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun CastsScreen(
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

        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(10) {
                CastItem(
                    painter = painterResource(id = R.drawable.charac),
                    Cast("")
                )
            }
        }
    }
}


@Composable
fun CastItem(
    painter: Painter,
    cast: Cast
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painter,
            modifier = Modifier
                .height(150.dp)
                .width(150.dp)
                .padding(8.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            contentDescription = "Character"
        )

        Text(
            text = "Ralph Fiennes",
            color = lightGray,
            fontWeight = FontWeight.ExtraLight,
            fontSize = 14.sp
        )
        
        Spacer(modifier = Modifier.height(8.dp))
    }
}