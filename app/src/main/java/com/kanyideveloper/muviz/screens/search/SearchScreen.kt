package com.kanyideveloper.muviz.screens.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanyideveloper.muviz.R
import com.kanyideveloper.muviz.model.Film
import com.kanyideveloper.muviz.presentation.components.StandardToolbar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        StandardToolbar(
            navigator = navigator,
            title = {
                Text(
                    text = "Search",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = true
        )

        LazyColumn(
            contentPadding = PaddingValues(8.dp)
        ) {
            items(10) {
                SearchItem(
                    modifier = Modifier
                        .fillMaxWidth(),
                    painter = painterResource(id = R.drawable.chosen),
                    Film("")
                )
            }
        }
    }
}

@Composable
fun SearchItem(
    modifier: Modifier = Modifier,
    painter: Painter,
    film: Film
) {
    Card(
        modifier = modifier.padding(4.dp)
    ) {
        Row(modifier = modifier) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .fillMaxHeight()
            ) {
                Image(
                    painter = painter,
                    modifier = Modifier
                        .fillMaxSize()
                        .height(110.dp),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }

            Column(
                modifier = modifier
                    .fillMaxWidth(0.7f)
                    .padding(8.dp)
            ) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "The Chosen",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "27/07/2022",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                LazyRow(
                    modifier = modifier.fillMaxWidth(),
                ) {
                    items(3){
                        Text(
                            modifier = modifier,
                            text = "Adventure . ",
                            color = Color.White,
                            fontWeight = FontWeight.Light,
                            fontSize = 10.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = modifier,
                    text = "The tale of an extraordinary family, the Madrigals, who live hidden in the mountains of Colombia",
                    color = Color.White,
                    fontWeight = FontWeight.Light,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 12.sp
                )
            }
        }
    }
}