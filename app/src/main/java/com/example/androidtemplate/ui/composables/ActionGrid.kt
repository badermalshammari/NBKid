package com.example.androidtemplate.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class ActionButtonData(
    val label: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@Composable
fun ActionGrid(buttons: List<ActionButtonData>) {
    Column {
        buttons.chunked(3).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach {
                    ActionButtonItem(icon = it.icon, label = it.label, onClick = it.onClick)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}