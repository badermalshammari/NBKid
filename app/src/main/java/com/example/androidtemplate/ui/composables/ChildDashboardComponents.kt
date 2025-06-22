package com.example.androidtemplate.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TaskCard(title: String, points: Int, gems: Int) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFF1F6FA),
        modifier = Modifier
            .width(180.dp)
            .height(100.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Task Name", fontSize = 12.sp, color = Color.Gray)
            Text(title, fontSize = 16.sp, color = Color(0xFF003366))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("🪙 $points", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                Text("💎 $gems", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
        }
    }
}

@Composable
fun StoreItemCard(name: String, gems: Int) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, Brush.linearGradient(listOf(Color.Magenta, Color.Red))),
        modifier = Modifier
            .width(100.dp)
            .height(130.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color.Magenta,
                modifier = Modifier.size(24.dp)
            )
            Text(name, fontSize = 14.sp, textAlign = TextAlign.Center)
            Text("💎 $gems", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
        }
    }
}

