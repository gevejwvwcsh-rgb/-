package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = AquaAccent,
    onPrimary = DarkBackground,
    primaryContainer = AquaContainerDark,
    onPrimaryContainer = AquaContainerLight,
    secondary = MetallicGold,
    onSecondary = DarkBackground,
    secondaryContainer = Color(0xFF453000),
    onSecondaryContainer = MetallicGoldLight,
    tertiary = AquaSecondary,
    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = DarkCard,
    onBackground = TextLight,
    onSurface = TextLight,
    outline = BorderDark
)

private val LightColorScheme = lightColorScheme(
    primary = AquaPrimary,
    onPrimary = PureWhite,
    primaryContainer = AquaContainerLight,
    onPrimaryContainer = AquaPrimaryVariant,
    secondary = MetallicGoldDark,
    onSecondary = PureWhite,
    secondaryContainer = MetallicGoldLight,
    onSecondaryContainer = Color(0xFF453000),
    tertiary = AquaSecondary,
    background = SlateBackgroundLight,
    surface = PureWhite,
    surfaceVariant = Color(0xFFF1F5F9),
    onBackground = TextDark,
    onSurface = TextDark,
    outline = BorderLight
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
