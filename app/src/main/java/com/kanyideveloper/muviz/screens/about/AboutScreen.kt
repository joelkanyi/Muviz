package com.kanyideveloper.muviz.screens.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanyideveloper.muviz.R
import com.kanyideveloper.muviz.screens.commons.StandardToolbar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun AboutScreen(
    navigator: DestinationsNavigator
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        StandardToolbar(
            navigator = navigator,
            title = {
                Text(
                    text = "About",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painterResource(
                id = R.drawable.muviz
            ),
            contentDescription = "App logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(8.dp)
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "V1.0.0",
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            text = stringResource(R.string.about),
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp
        )

    }
}