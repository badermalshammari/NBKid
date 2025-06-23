package com.example.androidtemplate.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidtemplate.ui.composables.*
import com.example.androidtemplate.viewmodels.CardScreenViewModel
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import com.example.androidtemplate.viewmodels.WalletViewModel

@Composable
fun ParentChildScreen(
    cardId: Long,
    mainViewModel: NBKidsViewModel,
    cardViewModel: CardScreenViewModel,
    walletViewModel: WalletViewModel,
    navController: NavController,
    onAddBalanceClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onTransferClick: () -> Unit = {},
    onLeaderboardClick: () -> Unit = {},
    onAddTaskClick: () -> Unit = {},
    onGiftsClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val parentId = mainViewModel.user?.parentId
    val cards by cardViewModel.cards.collectAsState()
    val selectedCard = cards.find { it.cardId == cardId }
    val wallet = walletViewModel.wallet

    // ✅ Only fetch cards if they haven't been loaded yet
    LaunchedEffect(parentId) {
        if (cards.isEmpty()) {
            parentId?.let {
                cardViewModel.fetchCards(it)
            }
        }
    }

    // ✅ Fetch wallet for the selected card
    LaunchedEffect(cardId) {
        walletViewModel.fetchWallet(cardId)
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            // Top bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("${selectedCard?.cardHolderName ?: "Child"} Account", fontWeight = FontWeight.Bold)
                    Text("(${selectedCard?.accountNumber ?: "••••••••"})", fontSize = 12.sp)
                }
                IconButton(onClick = { /* TODO: Handle notifications */ }) {
                    Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Card display
            selectedCard?.let { card ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .aspectRatio(1.6f)
                        .align(Alignment.CenterHorizontally)
                ) {
                    CreditCardComposable(card = card)
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Financial section using card.balance
            selectedCard?.let { card ->
                FinancialDetailSection(
                    balance = "KD ${card.balance}",
                    gems = wallet?.gems ?: 0,
                    points = wallet?.pointsBalance ?: 0
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Action grid
            ActionGrid(
                buttons = listOf(
                    ActionButtonData("Add Balance", Icons.Default.Add, onAddBalanceClick),
                    ActionButtonData("Settings", Icons.Default.Settings, onSettingsClick),
                    ActionButtonData("Transfer", Icons.Default.Send, onTransferClick),
                    ActionButtonData("Leaderboard", Icons.Default.Leaderboard, onLeaderboardClick),
                    ActionButtonData("Add Task", Icons.Default.PlaylistAdd, onAddTaskClick),
                    ActionButtonData("Gifts", Icons.Default.CardGiftcard, onGiftsClick)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Transactions
            Text("Latest Transactions", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                repeat(3) { index ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Transaction #${index + 1}")
                        Text("- KD ${(index + 1) * 1.250}")
                    }
                }
            }
        }
    }
}