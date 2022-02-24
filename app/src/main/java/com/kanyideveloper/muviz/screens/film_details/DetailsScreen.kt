package com.kanyideveloper.muviz.screens.film_details

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.*
import com.ramcosta.composedestinations.annotation.Destination
import kotlin.math.min
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.kanyideveloper.muviz.R
import com.kanyideveloper.muviz.model.Cast
import com.kanyideveloper.muviz.model.Film
import com.kanyideveloper.muviz.presentation.destinations.CastsScreenDestination
import com.kanyideveloper.muviz.ui.theme.*
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlin.math.max

@OptIn(ExperimentalAnimationApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Destination
@Composable
fun DetailsScreen(navigator: DestinationsNavigator) {
    val scrollState = rememberLazyListState()

    Box {
        FilmInfo(scrollState, Film(""), navigator = navigator)
        ProfileToolBar(scrollState, Film(""))
    }
}

@Composable
fun ProfileToolBar(
    scrollState: LazyListState,
    film: Film
) {
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
                    painter = painterResource(id = R.drawable.kings),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .height(imageHeight)
                        .graphicsLayer {
                            alpha = 1f - offsetProgress
                        }
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
                FilmNameAndRating(Film(""))
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
        CircularBackButtons()
        CircularFavoriteButtons()
    }
}

@Composable
fun FilmInfo(
    scrollState: LazyListState,
    film: Film,
    navigator: DestinationsNavigator
) {
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
                    text = "September 25th, 2021",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraLight,
                    color = Color.LightGray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "As a collection of history's worst tyrants and criminal masterminds gather to plot a war to wipe out millions, one man must race against time to stop them.",
                    fontSize = 13.sp,
                    color = Color.LightGray
                )
            }
        }
        item {
            CastDetails(navigator)
        }
    }
}

@Composable
fun FilmNameAndRating(
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
            Text(
                text = "The King's Man",
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
        colors = ButtonDefaults.buttonColors(
            backgroundColor = White.copy(alpha = 0.3f),
            contentColor = color
        )
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
fun CastDetails(navigator: DestinationsNavigator, modifier: Modifier = Modifier.fillMaxWidth()) {
    Column {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Cast",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = White,
                modifier = Modifier
                    .padding(
                        start = 8.dp
                    )
            )

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "View all",
                    fontWeight = FontWeight.ExtraLight,
                    fontSize = 18.sp,
                    color = White
                )

                IconButton(onClick = {
                    navigator.navigate(CastsScreenDestination)
                }) {
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
                    painter = painterResource(id = R.drawable.charac),
                    Cast("")
                )
            }
        })
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
                .fillMaxSize()
                .height(90.dp)
                .width(90.dp)
                .padding(8.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            contentDescription = "Character"
        )

        Text(
            text = "Ralph Fiennes",
            color = lightGray,
            fontWeight = FontWeight.ExtraLight,
            fontSize = 12.sp
        )
    }
}
