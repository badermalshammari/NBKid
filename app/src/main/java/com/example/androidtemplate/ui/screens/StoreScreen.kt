package com.example.androidtemplate.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.androidtemplate.navigation.Screen
import com.example.androidtemplate.ui.composables.StoreItemCard
import com.example.androidtemplate.ui.composables.ZuzuBottomNavBar
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import com.example.androidtemplate.viewmodels.TaskViewModel
import com.example.androidtemplate.viewmodels.WalletViewModel

@Composable
fun StoreScreen(
    nbkidsViewModel: NBKidsViewModel,
    navController: NavController,
) {

    val context = LocalContext.current
    val walletViewModel = remember { WalletViewModel(context) }
    val taskViewModel = remember { TaskViewModel(context) }

    var selectedTab by remember { mutableStateOf("Store") }
    val child = nbkidsViewModel.selectedChild

    LaunchedEffect(child) {
        child?.childId?.let {
            nbkidsViewModel.fetchStoreItems(it)
            walletViewModel.fetchWallet(it)
            taskViewModel.fetchTasks(it)
        }
    }

    val wallet by walletViewModel.walletState.collectAsState()
    val isLoading by walletViewModel.isLoading.collectAsState()
    val storeItems by nbkidsViewModel.storeitems

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
            // Header: Child Info & Wallet
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
                        text = "${child?.name}..",
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
                            painter = painterResource(id = getAvatarDrawable(child?.avatar)),
                            contentDescription = "Child Avatar",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        // Gems
                        Column {
                            Text("Available Gems", color = Color.Gray, fontSize = 14.sp)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.gems),
                                    contentDescription = "Gems",
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    "${wallet?.gems ?: 0}",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 14.sp
                                )
                            }
                            Text(
                                "= ${"%.3f".format((wallet?.gems ?: 0) / 1000.0)} KD",
                                fontSize = 10.sp,
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
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    "${wallet?.pointsBalance ?: 0}",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }

            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
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

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 600.dp)
            ) {

                items(storeItems) { item ->
                    val imageResId = remember(item.globalItem.photo) {
                        val resId = context.resources.getIdentifier(
                            item.globalItem.photo,
                            "drawable",
                            context.packageName
                        )
                        if (resId == 0) R.drawable.nbkidz_logo_white else resId
                    }

                    StoreItemCard(
                        item = item,
                        imageResId = imageResId,
                        canAfford = (wallet?.gems ?: 0) >= item.globalItem.costInGems,
                        onOrderClick = {
                            child?.childId?.let { id ->
//                                nbkidsViewModel.orderItem(id, item.globalItem.id)
                            }
                        }
                    )
                }
            }
        }
    }
}