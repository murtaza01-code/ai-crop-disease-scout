package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = ForestGreenPrimaryDark,
    onPrimary = ForestGreenOnPrimaryDark,
    primaryContainer = ForestGreenPrimaryContainerDark,
    onPrimaryContainer = ForestGreenOnPrimaryContainerDark,
    secondary = FoliageSecondaryDark,
    onSecondary = FoliageOnSecondaryDark,
    secondaryContainer = FoliageSecondaryContainerDark,
    onSecondaryContainer = FoliageOnSecondaryContainerDark,
    tertiary = SproutTertiary,
    onTertiary = SproutOnTertiary,
    background = AgriBackgroundDark,
    onBackground = AgriOnBackgroundDark,
    surface = AgriSurfaceDark,
    onSurface = AgriOnSurfaceDark,
    surfaceVariant = AgriSurfaceVariantDark,
    onSurfaceVariant = AgriOnSurfaceVariantDark,
    outline = AgriOutline,
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005)
)

private val LightColorScheme = lightColorScheme(
    primary = ForestGreenPrimary,
    onPrimary = ForestGreenOnPrimary,
    primaryContainer = ForestGreenPrimaryContainer,
    onPrimaryContainer = ForestGreenOnPrimaryContainer,
    secondary = FoliageSecondary,
    onSecondary = FoliageOnSecondary,
    secondaryContainer = FoliageSecondaryContainer,
    onSecondaryContainer = FoliageOnSecondaryContainer,
    tertiary = SproutTertiary,
    onTertiary = SproutOnTertiary,
    background = AgriBackground,
    onBackground = AgriOnBackground,
    surface = AgriSurface,
    onSurface = AgriOnSurface,
    surfaceVariant = AgriSurfaceVariant,
    onSurfaceVariant = AgriOnSurfaceVariant,
    outline = AgriOutline,
    error = AlertRed,
    onError = Color.White
)

@Composable
fun CropScoutTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep brand agricultural identity
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
