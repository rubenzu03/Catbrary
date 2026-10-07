package cat.rubenzu03.catbrary.ui.theme

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.core.content.edit
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext

enum class ThemeMode {

    SYSTEM,

    LIGHT,

    DARK,

    DYNAMIC,
    ;

    companion object {
        private const val PREFS_NAME = "catbrary_theme"
        private const val KEY_MODE = "theme_mode"

        fun load(context: Context): ThemeMode {
            val raw = context
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getString(KEY_MODE, null)
            return entries.firstOrNull { it.name == raw } ?: SYSTEM
        }

        fun save(context: Context, mode: ThemeMode) {
            context
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit { putString(KEY_MODE, mode.name) }
        }
    }
}

val LocalThemeMode = staticCompositionLocalOf { ThemeMode.SYSTEM }

@Composable
fun CatbraryTheme(
    themeMode: ThemeMode = LocalThemeMode.current,
    content: @Composable () -> Unit,
) {
    val darkTheme = when (themeMode) {
        ThemeMode.SYSTEM, ThemeMode.DYNAMIC -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val dynamic = themeMode == ThemeMode.DYNAMIC

    val colorScheme: ColorScheme = when {
        dynamic -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> WarmAmberDarkColorScheme
        else -> WarmAmberLightColorScheme
    }

    CompositionLocalProvider(LocalThemeMode provides themeMode) {
        MaterialExpressiveTheme(
            colorScheme = colorScheme,
            shapes = CatbraryShapes,
            typography = CatbraryTypography,
            motionScheme = MotionScheme.expressive(),
            content = content,
        )
    }
}
