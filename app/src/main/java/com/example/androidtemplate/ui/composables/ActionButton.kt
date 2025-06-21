package com.example.cheque_android.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ActionButton(
    label: String,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
    shape: Shape = CircleShape,
    buttonColor: Color = Color(0xFFE5EEF5),
    iconTint: Color = Color(0xFF005AAB),
    textColor: Color = Color.Black
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            shape = shape,
            color = buttonColor,
            modifier = Modifier
                .size(60.dp)
                .clickable { onClick() },
            shadowElevation = 4.dp
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                icon()
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}