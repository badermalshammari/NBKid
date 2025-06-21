package com.example.androidtemplate.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.androidtemplate.ui.composables.ActionRow
import com.example.androidtemplate.ui.composables.CreditCardComposable
import com.example.androidtemplate.ui.composables.SettingsToggle
import com.example.androidtemplate.viewmodels.CardScreenViewModel
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentCardsScreen(
    mainViewModel: NBKidsViewModel,
    cardViewModel: CardScreenViewModel,
    navController: NavController
) {
    val parentId = mainViewModel.user?.parentId

    val cards by cardViewModel.cards.collectAsState()
    val selectedCard by cardViewModel.selectedCard.collectAsState()
    val displayZuzu by cardViewModel.displayZuzu.collectAsState()

    // Fetch cards on first load
    LaunchedEffect(parentId) {
        parentId?.let { cardViewModel.fetchCards(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("YOUR CARDS") },
                navigationIcon = {
                    IconButton(onClick = { /* navigate to dashboard */ }) {
                        Icon(Icons.Default.Dashboard, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { /* show notifications */ }) {
                        Icon(Icons.Default.Notifications, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Card holder name and account number
            Text(
                selectedCard?.cardHolderName ?: "No Card Selected",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                "(${selectedCard?.accountNumber ?: "N/A"})",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            // Horizontal list of cards
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(cards) { card ->
                    Box(
                        modifier = Modifier
                            .width(300.dp)
                            .fillMaxHeight()
                            .clickable { cardViewModel.selectCard(card) }
                    ) {
                        CreditCardComposable(card)
                    }
                }
            }

            // Display selected card balance
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Available Balance", color = Color.Gray)
                Text(
                    text = "${selectedCard?.balance ?: 0.0} KD",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
            }

            // Action icons (Transactions, Statistics, Settings)
            ActionRow()

            // Toggle for displaying Zuzu accounts
            SettingsToggle(
                isChecked = displayZuzu,
                onToggle = { cardViewModel.toggleZuzuAccounts(it) }
            )
        }
    }
}