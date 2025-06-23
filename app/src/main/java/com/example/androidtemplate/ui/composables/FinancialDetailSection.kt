package com.example.androidtemplate.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FinancialDetailSection(balance: String, gems: Int, points: Int) {
    Column {
        Text("Available Balance", fontWeight = FontWeight.Bold)
        Text(balance, fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        Text("Gems", fontWeight = FontWeight.Bold)
        Text("💎 $gems", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(8.dp))

        Text("Points", fontWeight = FontWeight.Bold)
        Text("✨ $points", fontSize = 16.sp)
    }
}