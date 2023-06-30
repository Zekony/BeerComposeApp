package com.example.beercompapp.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val LightColorPalette = lightColors(
    primary = KellyGreen,
    primaryVariant = ForestGreen,
    secondary = ForestGreen,
    secondaryVariant = Green,
    background = PastelGreen,
    surface = MintGreen,
)
private val DarkColorPalette = darkColors(
    primary = ForestGreen,
    primaryVariant = DarkGreen,
    secondary = DarkGreen,
    secondaryVariant = KellyGreen,
    background = Color.Black,
    surface = HunterGreen,


    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)
/*private val GreenWithYellowColorPalette = lightColors(
    primary = KellyGreen,
    primaryVariant = KellyGreen,
    secondary = DarkKhaki,
    secondaryVariant = Malachite,
    background = PastelGreen,
    surface = Khaki,
    onSurface = HunterGreen
)

@SuppressLint("ConflictingOnColor")
private val GoldColorPalette = lightColors(
    primary = DarkGoldenrod,
    primaryVariant = Gold,
    secondary = Goldenrod,
    secondaryVariant = DarkGoldenrod,
    background = LightGoldenrod,
    surface = Gold,
    onSurface = Gold
)*/

@Composable
fun BeerCompAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}