package com.kanyideveloper.muviz.presentation.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanyideveloper.muviz.R
import com.kanyideveloper.muviz.domain.model.Film
import com.kanyideveloper.muviz.presentation.components.StandardToolbar
import com.kanyideveloper.muviz.presentation.film_details.CircularProgressIndicator
import com.kanyideveloper.muviz.presentation.film_details.FilmNameAndRating
import com.kanyideveloper.muviz.presentation.ui.theme.Transparent
import com.kanyideveloper.muviz.presentation.ui.theme.primaryDark
import com.kanyideveloper.muviz.presentation.ui.theme.primaryGray
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun FavoritesScreen(
    navigator: DestinationsNavigator
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        StandardToolbar(
            navigator = navigator,
            title = {
                Text(
                    text = "Favorites",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = false,
            navActions = {
                IconButton(onClick = {
                    //navController.navigate(Screen.SearchScreen.route)
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        )

        LazyColumn {
            items(10) {
                FilmItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    painter = painterResource(id = R.drawable.motherland)
                )
            }
        }
    }
}

@Composable
fun FilmItem(modifier: Modifier, painter: Painter) {
    Card(modifier = modifier.padding(4.dp)) {
        Box {
            Image(
                painter = painter,
                modifier = Modifier.fillMaxSize(),
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

            FilmDetails(film = Film(""))
        }
    }
}

@Composable
fun FilmDetails(
    film: Film
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Motherland Fort Salem",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "2021. Tv Series",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                )
            }
            CircularProgressIndicator(
                percentage = 0.75f
            )
        }
    }
}