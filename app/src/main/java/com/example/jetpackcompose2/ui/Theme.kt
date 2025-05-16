package com.example.jetpackcompose2.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.jetpackcompose2.ui.theme.BackgroundColor
import com.example.jetpackcompose2.ui.theme.ErrorRed
import com.example.jetpackcompose2.ui.theme.PrimaryColor
import com.example.jetpackcompose2.ui.theme.SecondaryColor

@Composable
fun MyFitnessTheme(content: @Composable () -> Unit) {
    val colorScheme = lightColorScheme(
        primary = PrimaryColor,
        secondary = SecondaryColor,
        background = BackgroundColor,
        error = ErrorRed,
        onPrimary = Color.White,
        onSecondary = Color.Black,
        onBackground = Color.Black,
        onError = Color.White
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(), // ✅ Sửa lỗi ở đây
        shapes = Shapes,
        content = content
    )
}
