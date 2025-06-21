package com.example.androidtemplate.ui.composables

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun FloatDown(
    modifier: Modifier = Modifier,
    visibleDuration: Int = 300,
    floatDownDistance: Dp = 60.dp,
    animationDuration: Int = 800,
    content: @Composable () -> Unit
) {
    var startDisappear by remember { mutableStateOf(false) }
    var removeFromScreen by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (startDisappear) 0f else 1f,
        animationSpec = tween(durationMillis = animationDuration),
        label = "floatAlpha"
    )

    val offsetY by animateDpAsState(
        targetValue = if (startDisappear) floatDownDistance else 0.dp,
        animationSpec = tween(durationMillis = animationDuration),
        label = "floatOffset"
    )

    LaunchedEffect(Unit) {
        delay(visibleDuration.toLong())
        startDisappear = true
        delay(animationDuration.toLong())
        removeFromScreen = true
    }

    if (!removeFromScreen) {
        Box(
            modifier = modifier
                .offset(y = offsetY)
                .alpha(alpha)
        ) {
            content()
        }
    }
}