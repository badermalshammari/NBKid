package com.example.androidtemplate.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun BottomNavigationBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(listOf(Color(0xFF0F4C81), Color(0xFF207DFF))),
                shape = RoundedCornerShape(30.dp)
            )
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Icon(Icons.Default.Person, contentDescription = null, tint = Color.White)
        Icon(Icons.Default.AccountBalanceWallet, contentDescription = null, tint = Color.White)
        Icon(Icons.Default.Chat, contentDescription = null, tint = Color.White)
        Icon(Icons.Default.Settings, contentDescription = null, tint = Color.White)
    }
}