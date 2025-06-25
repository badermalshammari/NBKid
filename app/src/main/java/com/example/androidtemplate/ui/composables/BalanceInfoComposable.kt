package com.example.androidtemplate.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun BalanceInfoComposable(
    availableBalance: String,
    availableGems: String,
    points: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Available Balance", fontSize = 12.sp, color = Color.Gray)
            Text(availableBalance, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Available Gems", fontSize = 12.sp, color = Color.Gray)
            Text(availableGems, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text("= 3.000 KD", fontSize = 12.sp, color = Color.Gray)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Points", fontSize = 12.sp, color = Color.Gray)
            Text(points, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}