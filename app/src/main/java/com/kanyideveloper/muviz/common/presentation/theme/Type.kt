/*
 * Copyright 2024 Joel Kanyi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kanyideveloper.muviz.common.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.kanyideveloper.muviz.R


val quicksand = FontFamily(
    Font(R.font.quicksand_light, FontWeight.Light),
    Font(R.font.quicksand_regular, FontWeight.Normal),
    Font(R.font.quicksand_medium, FontWeight.Medium),
    Font(R.font.quicksand_semibold, FontWeight.SemiBold),
    Font(R.font.quicksand_bold, FontWeight.Bold)
)


val Typography = Typography().run {
    copy(
        displayLarge = displayLarge.copy(
            fontFamily = quicksand
        ),
        displayMedium = displayMedium.copy(
            fontFamily = quicksand
        ),
        displaySmall = displaySmall.copy(
            fontFamily = quicksand
        ),
        headlineLarge = headlineLarge.copy(
            fontFamily = quicksand
        ),
        headlineMedium = headlineMedium.copy(
            fontFamily = quicksand
        ),
        headlineSmall = headlineSmall.copy(
            fontFamily = quicksand
        ),
        titleLarge = titleLarge.copy(
            fontFamily = quicksand,
            fontWeight = FontWeight.Bold
        ),
        titleMedium = titleMedium.copy(
            fontFamily = quicksand,
            fontWeight = FontWeight.Bold
        ),
        titleSmall = titleSmall.copy(
            fontFamily = quicksand,
            fontWeight = FontWeight.Bold
        ),
        bodyLarge = bodyLarge.copy(
            fontFamily = quicksand
        ),
        bodyMedium = bodyMedium.copy(
            fontFamily = quicksand
        ),
        bodySmall = bodySmall.copy(
            fontFamily = quicksand
        ),
        labelLarge = labelLarge.copy(
            fontFamily = quicksand
        ),
        labelMedium = labelMedium.copy(
            fontFamily = quicksand
        ),
        labelSmall = labelSmall.copy(
            fontFamily = quicksand
        ),
    )
}
