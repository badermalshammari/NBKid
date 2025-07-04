package com.example.androidtemplate.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidtemplate.R
import com.example.androidtemplate.navigation.Screen
import com.example.androidtemplate.viewmodels.CardScreenViewModel
import com.example.androidtemplate.viewmodels.WalletViewModel
import com.example.androidtemplate.ui.composables.CreditCardComposable
import com.example.androidtemplate.ui.composables.ButtonItemPreview
import com.example.androidtemplate.ui.composables.LoadingIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterCardScreen(
    cardId: Long,
    cardViewModel: CardScreenViewModel,
    walletViewModel: WalletViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val cards by cardViewModel.cards.collectAsState()
    val card = cards.find { it.cardId == cardId }
    val wallet by walletViewModel.walletState.collectAsState()
    val isLoading = cardViewModel.isLoading
    val childId = (wallet?.child?.childId ?: Long) as Long

    LaunchedEffect(cardId) {
        walletViewModel.fetchWallet(childId)
    }

    if (isLoading) {
        LoadingIndicator()
        return
    }

    if (card == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Card not found", color = Color.Red)
        }
        return
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = card.cardHolderName.replaceFirstChar { it.uppercaseChar() }, fontWeight = FontWeight.Bold)
                        Text(text = "(${card.accountNumber})", style = MaterialTheme.typography.labelSmall)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        Toast.makeText(context, "Refreshing...", Toast.LENGTH_SHORT).show()
                        navController.navigate(Screen.EnterCardScreen.route)

                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.6f)
                    .align(Alignment.CenterHorizontally)
                    .shadow(10.dp, shape = RoundedCornerShape(20.dp))
            ) {
                CreditCardComposable(card)
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Info Column
                        Column(verticalArrangement = Arrangement.spacedBy(50.dp)) {
                            // Balance
                            Column {
                                Text("Available Balance", color = Color.Gray, fontSize = 14.sp)
                                Text(
                                    "${card.balance} KD",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 25.sp
                                )
                            }
                            // Gems
                            Column {
                                Text("Available Gems", color = Color.Gray, fontSize = 14.sp)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(
                                        painter = painterResource(id = R.drawable.gems),
                                        contentDescription = "Gems",
                                        modifier = Modifier.size(25.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        "${wallet?.gems ?: 0}",
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 35.sp
                                    )
                                }
                                Text(
                                    "= ${"%.3f".format((wallet?.gems ?: 0) / 1000.0)} KD",
                                    fontSize = 20.sp,
                                    color = Color.Gray
                                )
                            }
                            // Points
                            Column {
                                Text("Points", color = Color.Gray, fontSize = 14.sp)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(
                                        painter = painterResource(id = R.drawable.points),
                                        contentDescription = "Points",
                                        modifier = Modifier.size(25.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        "${wallet?.pointsBalance ?: 0}",
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 35.sp
                                    )
                                }

                        }
                    }
                        Spacer(modifier = Modifier.width(16.dp))

                        // Buttons Columns
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            ButtonItemPreview(Icons.Default.Add, "Add Gems", Color(0xFFFFC107),
                                onClick = {
                                    card.childId?.let { childId ->
                                        navController.navigate("add_balance_screen/$childId")
                                    }
                                }
                            )
                            ButtonItemPreview(
                                icon = Icons.Default.PlaylistAdd,
                                title = "Add Task",
                                color = Color(0xFFEF5350),
                                onClick = { navController.navigate("add_task_screen/${card.cardId}") }
                            )
                            ButtonItemPreview(Icons.Default.Send, "Transfer", Color(0xFF7E57C2),
                                onClick = { navController.navigate(Screen.TransferScreen.route)}
                            )
                        }
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            ButtonItemPreview(Icons.Default.CardGiftcard, "Store", Color(0xFF66BB6A),
                                onClick = { navController.navigate("gifts_screen/${cardId}")}
                            )
                            ButtonItemPreview(Icons.Default.PlaylistAddCheck, "Check Tasks", Color(0xFFFF7043),
                                onClick = { navController.navigate(Screen.ParentCheckTask.route)})

                        ButtonItemPreview(Icons.Default.Star, "Leaderboard", Color(0xFFFFCA28),
                            onClick = { navController.navigate(Screen.LeaderboardParent.createRoute(cardId))}
                        )
                    }
                }
                }

                // Transactions Header
                item {
                    Column {
                        Text("Latest", fontWeight = FontWeight.Normal, fontSize = 15.sp)
                        Text("Transactions", fontWeight = FontWeight.Black, fontSize = 25.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // Sample Transactions
                items(2) { index ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                if (index == 0) Color(0xFF4CAF50).copy(alpha = 0.1f)
                                else Color(0xFFF44336).copy(alpha = 0.1f)
                            )
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    if (index == 0) "From Account" else "To Account",
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    if (index == 0) "12345678910" else "787478884",
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                if (index == 0) "+10KD" else "-30KD",
                                color = if (index == 0) Color(0xFF2E7D32) else Color(0xFFD32F2F),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
    }