package com.kanyideveloper.muviz.screens.film_details.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanyideveloper.muviz.data.remote.responses.Credits
import com.kanyideveloper.muviz.ui.theme.AppBarExpendedHeight
import com.kanyideveloper.muviz.util.Resource
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun FilmInfo(
    scrollState: LazyListState,
    releaseDate: String,
    overview: String,
    casts: Resource<Credits>,
    navigator: DestinationsNavigator
) {

    Spacer(modifier = Modifier.height(10.dp))

    LazyColumn(contentPadding = PaddingValues(top = AppBarExpendedHeight), state = scrollState) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = "Release date",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = releaseDate,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraLight,
                    color = Color.LightGray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = overview,
                    fontSize = 13.sp,
                    color = Color.LightGray
                )
            }
        }
        item {
            if (casts is Resource.Success){
                CastDetails(
                    casts.data!!,
                    navigator = navigator
                )
            }
        }
    }
}