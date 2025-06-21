package com.example.androidtemplate.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ActionRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ActionButtonItem(
            icon = Icons.AutoMirrored.Filled.Send,
            label = "Transactions",
            onClick = { /* TODO: navigate to Transactions */ }
        )
        ActionButtonItem(
            icon = Icons.Default.BarChart,
            label = "Statistics",
            onClick = { /* TODO: navigate to Statistics */ }
        )
        ActionButtonItem(
            icon = Icons.Default.Settings,
            label = "Settings",
            onClick = { /* TODO: navigate to Settings */ }
        )
    }
}