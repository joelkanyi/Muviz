package com.kanyideveloper.muviz.presentation.film_details

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanyideveloper.muviz.R
import com.kanyideveloper.muviz.presentation.ui.theme.AppBarExpendedHeight
import com.kanyideveloper.muviz.presentation.ui.theme.Transparent
import com.kanyideveloper.muviz.presentation.ui.theme.primaryDark
import com.kanyideveloper.muviz.presentation.ui.theme.primaryPink
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun DetailsScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
        ) {
            FilmBanner()
        }

        FilmInfo(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        CastDetails()

    }
}

@Composable
fun FilmBanner(
    imageUrl: String = ""
) {
    TopAppBar(
        contentPadding = PaddingValues(),
        modifier = Modifier.height(AppBarExpendedHeight)
    ) {
        Column {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.encanto),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
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
                FilmNameAndRating()
            }
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 5.dp)
    ) {
        CircularBackButtons()
        CircularFavoriteButtons()
    }
}

@Composable
fun FilmNameAndRating(
    name: String = "",
    percentage: Float = 0f
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
            Text(
                text = "Encanto",
                color = White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            CircularProgressIndicator(
                percentage = 0.75f
            )
        }
    }
}

@Composable
fun FilmInfo(
    modifier: Modifier = Modifier,
    releaseDate: String = "",
    description: String = "",
) {
    Column(modifier = modifier) {
        Text(
            text = "Release date",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = White
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "September 25th, 2021",
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraLight,
            color = LightGray
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "The tale of an extraordinary family, the Madrigals, who live hidden in the mountains of Colombia, in a magical house, in a vibrant town, in a wondrous, charmed place called an Encanto. The magic of the Encanto has blessed every child in the family with a unique gift from super strength to the power to heal—every child except one, Mirabel. But when she discovers that the magic surrounding the Encanto is in danger, Mirabel decides that she, the only ordinary Madrigal, might just be her exceptional family's last hope.",
            fontSize = 12.sp,
            /*textAlign = TextAlign.Justify,*/
            color = LightGray
        )

    }
}

@Composable
fun CircularBackButtons(
    color: Color = Color.Gray,
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    onClick: () -> Unit = {}
) {
    Button(
        onClick = { onClick() },
        contentPadding = PaddingValues(),
        shape = CircleShape,
        elevation = elevation,
        modifier = Modifier
            .width(38.dp)
            .height(38.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = color)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_left),
            contentDescription = null
        )
    }
}

@Composable
fun CircularFavoriteButtons(
    isLiked: Boolean = true,
    onClick: () -> Unit = {}
) {
    IconButton(onClick = {
        onClick()
    }) {
        Icon(
            modifier = Modifier
                .width(38.dp)
                .height(38.dp),
            imageVector = Icons.Filled.Favorite,
            tint = if (isLiked) {
                primaryPink
            } else {
                LightGray
            },
            contentDescription = if (isLiked) {
                stringResource(id = R.string.unlike)
            } else {
                stringResource(id = R.string.like)
            }
        )
    }
}

@Composable
fun CircularProgressIndicator(
    percentage: Float,
    number: Int = 100,
    fontSize: TextUnit = 16.sp,
    radius: Dp = 20.dp,
    color: Color = primaryPink,
    strokeWidth: Dp = 3.dp,
    animationDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }

    val currentPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animDelay
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius * 2f)
    ) {
        Canvas(
            modifier = Modifier
                .size(radius * 2f)
        ) {
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * currentPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = "${(currentPercentage.value * number).toInt()}%",
            color = primaryPink,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CastDetails(modifier: Modifier = Modifier.fillMaxWidth()) {
    Column()  {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Cast",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = White
            )

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Cast",
                    fontWeight = FontWeight.Light,
                    fontSize = 22.sp,
                    color = White
                )

                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chevron_right),
                        tint = primaryPink,
                        contentDescription = null
                    )
                }
            }
        }

        LazyRow(content = {
            items(10) {
                CastItem(
                    painter = painterResource(id = R.drawable.charac)
                )
            }
        })
    }
}

@Composable
fun CastItem(modifier: Modifier = Modifier, painter: Painter) {
        Image(
            painter = painter,
            modifier = Modifier
                .fillMaxSize()
                .height(100.dp)
                .width(100.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            contentDescription = "Movie Banner"
        )
}
