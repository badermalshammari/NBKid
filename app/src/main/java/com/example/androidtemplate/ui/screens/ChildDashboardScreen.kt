package com.example.androidtemplate.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import com.example.androidtemplate.ui.components.BottomNavigationBar
import com.example.androidtemplate.ui.components.StoreItemCard
import com.example.androidtemplate.ui.components.TaskCard
import com.example.androidtemplate.ui.composables.ZuzuBottomNavBar
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import com.example.androidtemplate.viewmodels.WalletViewModel

@Composable
fun ChildDashboardScreen(nbkidsViewModel: NBKidsViewModel) {
    val context = LocalContext.current
    val walletViewModel = remember { WalletViewModel(context) }

    val child = nbkidsViewModel.selectedChild

    LaunchedEffect(child) {
        child?.childId?.let { walletViewModel.fetchWallet(it) }
    }

    val wallet = walletViewModel.wallet
    val isLoading = walletViewModel.isLoading
    val errorMessage = walletViewModel.errorMessage

    if (child == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No child selected.", color = Color.Red)
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Welcome + Avatar + Gems/Points
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            // Left: Welcome and avatar
            Column(horizontalAlignment = Alignment.Start) {
                Text("Welcome", fontSize = 20.sp, color = Color.Black)
                Text(
                    text = "${child.name}..",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(16.dp))
                Surface(
                    modifier = Modifier.size(100.dp),
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 6.dp
                ) {
                    Image(
                        painter = painterResource(id = getAvatarDrawable(child.avatar)),
                        contentDescription = "Child Avatar",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Right: Gems and Points
            Column(horizontalAlignment = Alignment.End) {
                // Labels
                Row(horizontalArrangement = Arrangement.spacedBy(48.dp)) {
                    Text("Available Gems", fontSize = 14.sp, color = Color.Black)
                    Text("Points", fontSize = 14.sp, color = Color.Black)
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Values
                Row(
                    horizontalArrangement = Arrangement.spacedBy(48.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("💎", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = wallet?.gems?.toString() ?: "—",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🪙", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = wallet?.pointsBalance?.toString() ?: "—",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "= ${"%.3f".format((wallet?.pointsBalance ?: 0) / 1000.0)} KD",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        if (isLoading) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        if (errorMessage != null) {
            Text("Error loading wallet: $errorMessage", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Divider
        Box(
            modifier = Modifier
                .height(4.dp)
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(Color(0xFF8E2DE2), Color(0xFFF27121))
                    )
                )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // To Do Tasks
        Text("To Do Tasks", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            TaskCard(title = "Clean Your Room", points = 50, gems = 1000)
            TaskCard(title = "Wash Dishes", points = 40, gems = 800)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Store
        Text("Store", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            StoreItemCard(name = "Toy House", gems = 99900)
            StoreItemCard(name = "Book", gems = 3500)
            StoreItemCard(name = "Surprise Box", gems = 15000)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Bottom NavBar
        ZuzuBottomNavBar(selected = "Tasks", onItemSelected = {})
    }
}