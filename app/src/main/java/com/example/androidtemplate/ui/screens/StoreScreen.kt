package com.example.androidtemplate.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidtemplate.R
import com.example.androidtemplate.data.dtos.mockStoreItems
import com.example.androidtemplate.navigation.Screen
import com.example.androidtemplate.ui.composables.ZuzuBottomNavBar
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import com.example.androidtemplate.viewmodels.TaskViewModel
import com.example.androidtemplate.viewmodels.WalletViewModel

@Composable
fun StoreScreen(
    nbkidsViewModel: NBKidsViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val walletViewModel = remember { WalletViewModel(context) }
    val taskViewModel = remember { TaskViewModel(context) }

    var selectedTab by remember { mutableStateOf("Store") }

    val child = nbkidsViewModel.selectedChild

    LaunchedEffect(child) {
        child?.childId?.let {
            walletViewModel.fetchWallet(it)
            taskViewModel.fetchTasks(it)
        }
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

    Scaffold(
        bottomBar = {
            ZuzuBottomNavBar(selected = selectedTab) { item ->
                selectedTab = item
                when (item) {
                    "Home" -> navController.navigate(Screen.ChildDashboardScreen.route)
                    "Tasks" -> navController.navigate(Screen.TaskScreen.route)
                    "Store" -> navController.navigate(Screen.StoreScreen.route)
                    "Leaderboard" -> navController.navigate(Screen.LeaderboardScreen.route)
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = 24.dp,
                    end = 24.dp,
                    bottom = innerPadding.calculateBottomPadding()
                )
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
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

                Column(horizontalAlignment = Alignment.End) {
                    Row(horizontalArrangement = Arrangement.spacedBy(48.dp)) {
                        Text("Available Gems", fontSize = 14.sp, color = Color.Black)
                        Text("Points", fontSize = 14.sp, color = Color.Black)
                    }

                    Spacer(modifier = Modifier.height(6.dp))

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

            Text(
                text = "Available Store Items",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 0.dp, max = 1000.dp) // allow it to grow with scroll
            ) {
                items(mockStoreItems) { item ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(6.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFECEFF1)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .clickable {
                                // TODO: handle item purchase
                            }
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = item.imageResId),
                                contentDescription = item.name,
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                            )
                            Text(text = item.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(text = "${item.costInGems} 💎", fontSize = 14.sp, color = Color.Gray)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp)) // extra space before nav bar
        }
    }
}