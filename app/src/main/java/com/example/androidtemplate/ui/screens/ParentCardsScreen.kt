package com.example.androidtemplate.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidtemplate.R
import com.example.androidtemplate.navigation.Screen
import com.example.androidtemplate.ui.composables.*
import com.example.androidtemplate.utils.Logout
import com.example.androidtemplate.viewmodels.CardScreenViewModel
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import com.example.androidtemplate.viewmodels.WalletViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentCardsScreen(
    mainViewModel: NBKidsViewModel,
    cardViewModel: CardScreenViewModel,
    walletViewModel: WalletViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val parentId = mainViewModel.user?.parentId

    var selectedTab by remember { mutableStateOf("Wallet") }
    val cards by cardViewModel.cards.collectAsState()
    val displayZuzu by cardViewModel.displayZuzu.collectAsState()
    val wallet by walletViewModel.walletState.collectAsState()
    val selectedCard by cardViewModel.selectedCard.collectAsState()

    val isRefreshing = cardViewModel.isLoading

    LaunchedEffect(parentId) {
        parentId?.let { cardViewModel.fetchCards(it) }
    }

    LaunchedEffect(selectedCard) {
        val card = selectedCard
        if (card != null && !card.isParentCard && card.childId != null) {
            walletViewModel.fetchWallet(card.childId)
        }
    }

    val parentCards = cards.filter { it.isParentCard }
    val kidCards = cards.filter { !it.isParentCard }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("YOUR CARDS", fontWeight = FontWeight.Black) },
                navigationIcon = {
                    IconButton(onClick = { Logout(mainViewModel, navController) }) {
                        Icon(Icons.Default.Logout, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            ParentBottomNavBar(selected = selectedTab) {
                selectedTab = it
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
                    Text("Personal Cards", color = Color.Gray)
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyRow(
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(parentCards) { card ->
                            Box(
                                modifier = Modifier
                                    .width(300.dp)
                                    .fillMaxHeight()
                                    .clickable { cardViewModel.selectCard(card) }
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
                                        cardViewModel.createParentCard(
                                            parentId = mainViewModel.user?.parentId ?: return@clickable,
                                            onSuccess = {
                                                Toast.makeText(context, "New card created!", Toast.LENGTH_SHORT).show()
                                            },
                                            onError = {
                                                Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_LONG).show()
                                            }
                                        )
                                    }
                            ) {
                                AddnewCreditCardComposable(true)
                            }
                        }
                    }
                }
                if (displayZuzu) {
                    item {
                        Text("NBKidz Cards", color = Color.Gray)
                        Spacer(modifier = Modifier.height(20.dp))
                        LazyRow(
                            modifier = Modifier.fillMaxWidth().height(200.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(kidCards) { card ->
                                Box(
                                    modifier = Modifier
                                        .width(300.dp)
                                        .fillMaxHeight()
                                        .clickable { cardViewModel.selectCard(card) }
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
                                    AddnewCreditCardComposable(false)
                                }
                            }
                        }
                    }
                }

                item {
                    Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.Gray, thickness = 1.dp)
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
                        if (selectedCard?.isParentCard == false) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                                    Image(painter = painterResource(id = R.drawable.points), contentDescription = "points", modifier = Modifier.size(40.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Points ${wallet?.pointsBalance ?: 0}", fontWeight = FontWeight.Bold)
                                }
                                Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                                    Image(painter = painterResource(id = R.drawable.gems), contentDescription = "gems", modifier = Modifier.size(40.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Gems ${wallet?.gems ?: 0}", fontWeight = FontWeight.Bold)
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("Available Balance", color = Color.Gray)
                                    Text("${selectedCard?.balance ?: 0.0} KD", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                                }
                            }
                        } else {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("Available Balance", color = Color.Gray)
                                    Text("${selectedCard?.balance ?: 0.0} KD", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ActionButtonItem(icon = Icons.AutoMirrored.Filled.Send, label = "Transfer", onClick = {navController.navigate(
                            Screen.TransferScreen.route)})
                        ActionButtonItem(icon = Icons.Default.BarChart, label = "Statistics", onClick = {})
                        ActionButtonItem(
                            icon = Icons.Default.Settings,
                            label = "Settings",
                            onClick = {
                                selectedCard?.cardId?.let {
                                    navController.navigate("enter_card_screen/$it")
                                }
                            }
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
}