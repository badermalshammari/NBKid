package com.example.androidtemplate.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ViewTaskCard(title: String, points: Int, gems: Int) {
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
                Text("🪙 $points", fontWeight = FontWeight.Bold)
                Text("💎 $gems", fontWeight = FontWeight.Bold)
            }
        }
    }
}
