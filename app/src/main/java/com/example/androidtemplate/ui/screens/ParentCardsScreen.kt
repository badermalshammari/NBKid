package com.example.androidtemplate.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Settings
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
import com.example.androidtemplate.navigation.Screen
import com.example.androidtemplate.ui.composables.ActionButtonItem
import com.example.androidtemplate.ui.composables.AddnewCreditCardComposable
import com.example.androidtemplate.ui.composables.CreditCardComposable
import com.example.androidtemplate.ui.composables.SettingsToggle
import com.example.androidtemplate.utils.Logout
import com.example.androidtemplate.viewmodels.CardScreenViewModel
import com.example.androidtemplate.viewmodels.NBKidsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentCardsScreen(
    mainViewModel: NBKidsViewModel,
    cardViewModel: CardScreenViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val parentId = mainViewModel.user?.parentId

    val cards by cardViewModel.cards.collectAsState()
    val selectedCard by cardViewModel.selectedCard.collectAsState()
    val displayZuzu by cardViewModel.displayZuzu.collectAsState()

    LaunchedEffect(parentId) {
        parentId?.let {
            cardViewModel.fetchCards(it)
        }
    }

    LaunchedEffect(cards) {
        cards.forEach {
            println(context.resources.getIdentifier(it.cardDesign, "drawable", context.packageName))
            println("Card: ${it.cardHolderName}, isParent: ${it.isParentCard}")
        }
    }

    // Show parent cards always; show child cards only if toggle is ON
    val visibleCards = cards.filter { it.isParentCard || displayZuzu }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("YOUR CARDS") },
                navigationIcon = {
                    IconButton(onClick = { Logout(mainViewModel, navController) }) {
                        Icon(Icons.Default.Logout, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("YOUR CARDS", color = Color.Gray)
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(visibleCards) { card ->
                        Box(
                            modifier = Modifier
                                .width(300.dp)
                                .fillMaxHeight()
                                .clickable {
                                    cardViewModel.selectCard(card)
                                    Log.d("CARD_CLICK", "Clicked cardId = ${card.cardId}, holder = ${card.cardHolderName}")
                                    if (!card.isParentCard) {
                                        // Navigate to child card screen
                                        navController.navigate("parent_child_screen/${card.cardId}")
                                    }
                                }
                        ) {
                            CreditCardComposable(card)
                        }
                    }
                    item {
                        Box(
                            modifier = Modifier
                                .width(300.dp)
                                .fillMaxHeight()
                                .clickable {
                                    navController.navigate(Screen.CreateNewChildAccount.route)
                                }
                        ) {
                            AddnewCreditCardComposable(true)
                        }
                    }
                }
            }

            item {
                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.Gray,
                    thickness = 1.dp
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "(${selectedCard?.cardHolderName?.uppercase() ?: "No Card Selected"})",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                fontSize = 23.sp,
                                color = Color.Black
                            )
                            Text(
                                "(${selectedCard?.accountNumber ?: "N/A"})",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Available Balance", color = Color.Gray)
                            Text(
                                text = "${selectedCard?.balance ?: 0.0} KD",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ActionButtonItem(
                        icon = Icons.AutoMirrored.Filled.Send,
                        label = "Transactions",
                        onClick = { /* TODO */ }
                    )
                    ActionButtonItem(
                        icon = Icons.Default.BarChart,
                        label = "Statistics",
                        onClick = { /* TODO */ }
                    )
                    ActionButtonItem(
                        icon = Icons.Default.Settings,
                        label = "Settings",
                        onClick = { /* TODO */ }
                    )
                }
            }

            item {
                SettingsToggle(
                    isChecked = displayZuzu,
                    onToggle = { cardViewModel.toggleZuzuAccounts(it) }
                )
            }
        }
    }
}