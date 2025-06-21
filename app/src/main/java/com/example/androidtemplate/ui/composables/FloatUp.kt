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

@Composable
fun FloatUp(
    modifier: Modifier = Modifier,
    durationMillis: Int = 800,
    floatUpDistance: Dp = 30.dp,
    delayMillis: Int = 0,
    content: @Composable () -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis)
    )

    val offsetY by animateDpAsState(
        targetValue = if (visible) 0.dp else floatUpDistance,
        animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis)
    )

    LaunchedEffect(Unit) {
        visible = true
    }

    Box(
        modifier = modifier
            .offset(y = offsetY)
            .alpha(alpha)
    ) {
        content()
    }
}