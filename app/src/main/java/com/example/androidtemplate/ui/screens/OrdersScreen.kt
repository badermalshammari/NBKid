package com.example.androidtemplate.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidtemplate.R
import com.example.androidtemplate.navigation.Screen
import com.example.androidtemplate.ui.composables.Header
import com.example.androidtemplate.ui.composables.OrderItemCard
import com.example.androidtemplate.ui.composables.TypingText
import com.example.androidtemplate.ui.composables.ZuzuBottomNavBar
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import com.example.androidtemplate.viewmodels.WalletViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun OrdersScreen(
    viewModel: WalletViewModel,
    navController: NavController,
    nbkidsViewModel: NBKidsViewModel,
) {
    val wallet by viewModel.walletState.collectAsState()
    val child = nbkidsViewModel.selectedChild

    var selectedTab by remember { mutableStateOf("Orders") }

    val orders = viewModel.orders
    val isLoadingOrders = viewModel.is_Loading
    val errorOrders = viewModel.error_Message

    // Swipe refresh state
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoadingOrders)

    // Fetch data when child changes
    LaunchedEffect(child) {
        child?.childId?.let {
            nbkidsViewModel.fetchStoreItems(it)
            viewModel.fetchWallet(it)
            viewModel.fetchOrders(it)
        }
    }

    Scaffold(
        bottomBar = {
            ZuzuBottomNavBar(
                navController = navController,
                selected = selectedTab,
                onItemSelected = { item ->
                    selectedTab = item
                    when (item) {
                        "Orders" -> navController.navigate(Screen.OrdersScreen.route)
                        "Tasks" -> navController.navigate(Screen.TaskScreen.route)
                        "Store" -> navController.navigate(Screen.StoreScreen.route)
                        "Leaderboard" -> navController.navigate(Screen.LeaderboardScreen.route)
                    }
                }
            )
        }
    ) { paddingValues ->

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                child?.childId?.let {
                    viewModel.fetchOrders(it)
                }
            },
            modifier = Modifier
                .fillMaxSize()){
                    if (wallet == null) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Text("Loading wallet...")
                }
            } else {
                when {
                    isLoadingOrders && orders.isEmpty() -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    errorOrders != null -> {
                        Text("Error: $errorOrders", color = Color.Red)
                    }

                    orders.isEmpty() -> {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            if (child != null) {
                                Header(child = child, wallet = wallet)
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                            Column(
                                modifier = Modifier.fillMaxSize().padding(paddingValues),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                TypingText(
                                    "No orders found.",
                                    color = Color(0xFF848484),
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }

                    else -> {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            if (child != null) {
                                Header(child = child, wallet = wallet)
                                Spacer(modifier = Modifier.height(16.dp))
                            }


                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(16.dp)
                            ) {
                                items(orders) { order ->
                                    OrderItemCard(order)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}