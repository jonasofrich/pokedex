package com.example.pokedex.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Blue,
    secondary = Red,
    tertiary = Yellow,
    surface = Red,
    background = Background,
    onSurface = Text
)

private val LightColorScheme = lightColorScheme(
    primary = Blue,
    secondary = Red,
    tertiary = Yellow,
    surface = Red,
    background = Background,
    onSurface = Text
)

@Composable
fun PokedexTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}