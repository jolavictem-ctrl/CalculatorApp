package com.cisgroup8.calculatorapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = OperatorButton,
    secondary = EqualButton,
    tertiary = ClearButton,
    background = BackgroundDark,
    surface = BackgroundDark,
    onPrimary = TextWhite,
    onSecondary = TextWhite,
    onTertiary = TextWhite,
    onBackground = TextWhite,
    onSurface = TextWhite
)

private val LightColorScheme = lightColorScheme(
    primary = OperatorButton,
    secondary = EqualButton,
    tertiary = ClearButton,
    background = BackgroundLight,
    surface = BackgroundLight,
    onPrimary = TextBlack,
    onSecondary = TextWhite,
    onTertiary = TextWhite,
    onBackground = TextBlack,
    onSurface = TextBlack
)

@Composable
fun CalculatorAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
