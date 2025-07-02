package com.example.androidtemplate.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
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
import com.example.androidtemplate.ui.composables.*
import com.example.androidtemplate.viewmodels.CardScreenViewModel
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import com.example.androidtemplate.viewmodels.WalletViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardSettingsScreen(
    mainViewModel: NBKidsViewModel,
    cardViewModel: CardScreenViewModel,
    walletViewModel: WalletViewModel,
    navController: NavController
) {
    val parentId = mainViewModel.user?.parentId
    var selectedTab by remember { mutableStateOf("Wallet") }
    val cards by cardViewModel.cards.collectAsState()
    val selectedCard by cardViewModel.selectedCard.collectAsState()
    val context = LocalContext.current
    val isCardActive = selectedCard?.isActive
    val isRefreshing = cardViewModel.isLoading

    var filterStatus by remember { mutableStateOf("All") }

    LaunchedEffect(parentId) {
        parentId?.let { cardViewModel.fetchCards(it) }

        val card = selectedCard
        if (card != null && !card.isParentCard && card.childId != null) {
            walletViewModel.fetchWallet(card.childId)
        }
    }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Black) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        Toast.makeText(context, "Refreshing...", Toast.LENGTH_SHORT).show()
                        navController.navigate(Screen.CardSettingsScreen.route)

                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }

            )
        },
        bottomBar = {
            ParentBottomNavBar(selected = "Settings") { item ->
                selectedTab = item
                when (item) {
                    "Person" -> navController.navigate(Screen.ParentCardsScreen.route)
                    "Switch" -> navController.navigate(Screen.SelectKidScreen.route)
                    "Settings" -> navController.navigate(Screen.ParentCardsScreen.route)
                }
            }
        }
    ) { padding ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { parentId?.let { cardViewModel.fetchCards(it) } }
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text("All Cards", color = Color.Gray)
                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val options = listOf("All", "Active", "Disabled")
                        options.forEach { option ->
                            Button(
                                onClick = { filterStatus = option },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (filterStatus == option) Color(0xFF36709E) else Color.LightGray
                                ),
                                shape = RoundedCornerShape(50.dp),
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .height(40.dp)
                            ) {
                                Text(option, color = Color.White)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    val filteredCards = cards.filter {
                        when (filterStatus) {
                            "Active" -> it.isActive
                            "Disabled" -> !it.isActive
                            else -> true
                        }
                    }

                    LazyRow(
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(filteredCards) { card ->
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
                }

                item {
                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = Color.Gray,
                        thickness = 1.dp
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                selectedCard?.cardHolderName?.uppercase() ?: "No Card Selected",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                color = Color.Black
                            )
                            Text(
                                "(${selectedCard?.accountNumber ?: "N/A"})",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                                fontSize = 15.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                selectedCard?.let { card ->
                                    isCardActive?.let {
                                        cardViewModel.toggleCardActivation(
                                            card.cardId,
                                            !it,
                                            onSuccess = {
                                                Toast.makeText(context, "Card status updated", Toast.LENGTH_SHORT).show()
                                            },
                                            onError = {
                                                Toast.makeText(context, "Failed to update: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
                                            }
                                        )
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isCardActive == true) Color(0xFFBE1010) else Color(0xFF4CAF50)
                            ),
                            shape = RoundedCornerShape(50.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(if (isCardActive == true) "Disable Card" else "Enable Card")
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF376F9D)
                            ),
                            onClick = {
                                navController.navigate(Screen.CardSettingsScreen.route)
                            },
                            ){
                            Text("Refresh")
                        }
                    }
                }
            }
        }
    }
}