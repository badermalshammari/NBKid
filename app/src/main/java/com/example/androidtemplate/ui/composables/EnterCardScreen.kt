package com.example.androidtemplate.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidtemplate.data.dtos.BankCardDto
import com.example.androidtemplate.data.dtos.WalletResponseDto

@Composable
fun EnterCardScreen(card: BankCardDto, wallet: WalletResponseDto) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(card.cardHolderName, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                Text("(${card.accountNumber})", fontSize = 14.sp)
            }
            Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Card
        CreditCardComposable(card)

        Spacer(modifier = Modifier.height(24.dp))

        // Balance and Info
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text("Available Balance", color = Color.Gray)
                Text("${card.balance} KD", fontWeight = FontWeight.Bold, fontSize = 22.sp)
            }
            Column {
                Text("Available Gems", color = Color.Gray)
                Text("💎 ${wallet.gems}", fontWeight = FontWeight.Bold)
                Text("= ${wallet.gems / 1000.0} KD", fontSize = 12.sp)
            }
            Column {
                Text("Points", color = Color.Gray)
                Text("🏅 ${wallet.pointsBalance}", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        val buttons = listOf(
            Triple("Add Balance", Icons.Default.Add, Color(0xFFFFC107)),
            Triple("Settings", Icons.Default.Settings, Color(0xFFFF7043)),
            Triple("Transfer", Icons.Default.Send, Color(0xFF7E57C2)),
            Triple("Leaderboard", Icons.Default.Star, Color(0xFFFFCA28)),
            Triple("Add Task", Icons.Default.List, Color(0xFFEF5350)),
            Triple("Gifts", Icons.Default.CardGiftcard, Color(0xFF66BB6A))
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(buttons) { (title, icon, color) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(color),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = icon, contentDescription = title, tint = Color.White)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(title, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}