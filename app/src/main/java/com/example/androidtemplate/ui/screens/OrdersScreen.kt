package com.example.androidtemplate.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.androidtemplate.data.dtos.OrderedItemDto
import com.example.androidtemplate.viewmodels.WalletViewModel

@Composable
fun OrdersScreen(viewModel: WalletViewModel) {
    val wallet by viewModel.walletState.collectAsState()
    val orders = viewModel.orders
    val isLoading = viewModel.is_Loading
    val error = viewModel.error_Message

    val childId = wallet?.child?.childId

    // Fetch orders when childId is available
    LaunchedEffect(childId) {
        if (childId != null) {
            println("Fetching orders for childId = $childId")
            viewModel.fetchOrders(childId)
        }
    }

    // UI Rendering
    when {
        wallet == null || childId == null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Loading child info...")
            }
        }
        isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: $error")
            }
        }
        orders.isEmpty() -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No orders yet.")
            }
        }
        else -> {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(orders) { order ->
                    OrderItemCard(order)
                }
            }
        }
    }
}

@Composable
fun OrderItemCard(order: OrderedItemDto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text("Item: ${order.itemName}", fontWeight = FontWeight.Bold)
            Text("Status: ${order.status}")
            Text("Gems: ${order.gemsCost}")
            Text("Date: ${order.orderedAt}")

            val context = LocalContext.current
            val imageResId = remember(order.itemImageUrl) {
                context.resources.getIdentifier(order.itemImageUrl, "drawable", context.packageName)
            }

            if (imageResId != 0) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = order.itemName,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )
            }
        }
    }
}