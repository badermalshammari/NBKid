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
        Text("$balance KD", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        Text("Available Gems")
        Text("💎$gems • ${String.format("%.3f", gems / 1000.0)} KD", fontWeight = FontWeight.SemiBold)

        Spacer(modifier = Modifier.height(8.dp))

        Text("Points")
        Text("✨$points", fontWeight = FontWeight.SemiBold)
    }
}