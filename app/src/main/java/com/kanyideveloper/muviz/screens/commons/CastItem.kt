package com.kanyideveloper.muviz.screens.commons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.kanyideveloper.muviz.R
import com.kanyideveloper.muviz.ui.theme.lightGray

@Composable
fun CastItem(
    size: Dp,
    castName: String,
    castImageUrl: String
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = rememberImagePainter(
                data = castImageUrl,
                builder = {
                    placeholder(R.drawable.ic_placeholder)
                    crossfade(true)
                }
            ),
            modifier = Modifier
                .fillMaxSize()
                .size(size)
                .padding(8.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            contentDescription = "Character"
        )

        Text(
            text = castName,
            color = lightGray,
            fontWeight = FontWeight.ExtraLight,
            fontSize = 11.sp
        )
    }
}