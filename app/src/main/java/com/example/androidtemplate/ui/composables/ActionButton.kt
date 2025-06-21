package com.example.androidtemplate.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ActionButton(label: String, icon: @Composable () -> Unit, onClick: () -> Unit) {

    val textColor = Color.White
    val buttonColor  = Color(0xFF2ED2C0)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            shape = RoundedCornerShape(16.dp),
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

            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                icon()
            }
        }
        Spacer(modifier = Modifier.height(4.dp))

        Text(label, style = MaterialTheme.typography.labelSmall, color = textColor)
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}