package com.example.androidtemplate.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.androidtemplate.navigation.Screen
import com.example.androidtemplate.ui.composables.OrderItemCard
import com.example.androidtemplate.ui.composables.ZuzuBottomNavBar
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import com.example.androidtemplate.viewmodels.WalletViewModel

@Composable
fun OrdersScreen(
    viewModel: WalletViewModel,
    navController: NavController,
    nbkidsViewModel: NBKidsViewModel,
) {
    val walletState by viewModel.walletState.collectAsState()
    val child = nbkidsViewModel.selectedChild

    var selectedTab by remember { mutableStateOf("Orders") }

    val orders = viewModel.orders
    val isLoadingOrders = viewModel.is_Loading
    val errorOrders = viewModel.error_Message


    // Fetch orders only after wallet is loaded
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (walletState == null) {
                CircularProgressIndicator()
                Text("Loading wallet...")
            } else {
                when {
                    isLoadingOrders -> CircularProgressIndicator()
                    errorOrders != null -> Text("Error: $errorOrders", color = Color.Red)
                    orders.isEmpty() -> Text("No orders found.")
                    else -> {
                        LazyColumn {
                            items(orders) { order ->
                                OrderItemCard(order)
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}