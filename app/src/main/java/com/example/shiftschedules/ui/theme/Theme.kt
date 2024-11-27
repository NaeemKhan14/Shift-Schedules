package com.example.shiftschedules.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext

// Define your light and dark color schemes
private val DarkColorScheme = darkColorScheme(
    primary = CardPrimary,
    secondary = CardSecondary,
    tertiary = CardTertiary
)

private val LightColorScheme = lightColorScheme(
    primary = CardPrimary,
    secondary = CardSecondary,
    tertiary = CardTertiary
)

// Composable function to define the app's theme
@Composable
fun ShiftSchedulesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Apply the theme and set status bar color
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Use typography from Typography.kt
        content = content
    )

    // Update status bar color based on the current theme's primary color
    (LocalContext.current as? Activity)?.window?.statusBarColor = colorScheme.primary.toArgb()
}
