package com.example.androidtemplate.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SendButton(onClick: () -> Unit) {

    Box(
        modifier = Modifier
            .size(width = 300.dp, height = 50.dp)
        .background(
                Brush.horizontalGradient(
                    listOf(Color(0xFF8E2DE2), Color(0xFFF27121))
                ),
                shape = RoundedCornerShape(50)
            )
            .clickable(){onClick},
        contentAlignment = Alignment.Center
    )  {
        Text(
            text = "Send",
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}