package com.example.androidtemplate.ui.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun TypingText(text: String,
               speed: Long = 50L,
               fontWeight: FontWeight = FontWeight.Bold,
               fontSize: TextUnit = 35.sp,
               color: Color = Color.White) {
    var visibleText by remember { mutableStateOf("") }

    LaunchedEffect(text) {
        visibleText = ""
        for (i in text.indices) {
            visibleText += text[i]
            delay(speed)
        }
    }

    Text(
        text = visibleText,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        style = MaterialTheme.typography.bodyLarge.copy(
            textDirection = TextDirection.Ltr
        ),
        textAlign = TextAlign.Center
    )
}