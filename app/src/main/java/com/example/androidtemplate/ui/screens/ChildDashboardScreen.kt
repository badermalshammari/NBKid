package com.example.androidtemplate.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.androidtemplate.R
import com.example.androidtemplate.navigation.Screen
import com.example.androidtemplate.ui.composables.Header
import com.example.androidtemplate.ui.composables.StoreItemCard
import com.example.androidtemplate.ui.composables.ZuzuBottomNavBar
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import com.example.androidtemplate.viewmodels.TaskViewModel
import com.example.androidtemplate.viewmodels.WalletViewModel
import com.google.accompanist.swiperefresh.*

@Composable
fun ChildDashboardScreen(
    nbkidsViewModel: NBKidsViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val walletViewModel = remember { WalletViewModel(context) }
    val taskViewModel = remember { TaskViewModel(context) }

    var selectedTab by remember { mutableStateOf("Home") }
    var isRefreshing by remember { mutableStateOf(false) }

    val child = nbkidsViewModel.selectedChild
    val storeItems by nbkidsViewModel.storeitems
    val wallet by walletViewModel.walletState.collectAsState()
    val isLoading by walletViewModel.isLoading.collectAsState()

    val tasks = taskViewModel.tasks
    val tasksLoading = taskViewModel.isLoading
    val tasksError = taskViewModel.errorMessage

    LaunchedEffect(child) {
        child?.childId?.let {
            walletViewModel.fetchWallet(it)
            taskViewModel.fetchTasks(it)
            nbkidsViewModel.fetchStoreItems(it)
        }
    }

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

        val visibleItems = storeItems.filter { !it.isHidden }
        val swipeState = rememberSwipeRefreshState(isRefreshing)

        SwipeRefresh(
            state = swipeState,
            onRefresh = {
                isRefreshing = true
                child?.childId?.let {
                    nbkidsViewModel.fetchStoreItems(it)
                    walletViewModel.fetchWallet(it)
                    taskViewModel.fetchTasks(it)
                }
                isRefreshing = false
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {

                item(span = { GridItemSpan(2) }) {
                    Column (horizontalAlignment = Alignment.CenterHorizontally){
                        Header(child = child, wallet = wallet)
                        Spacer(modifier = Modifier.height(16.dp))

                        val toDoCount = tasks.count { it.status != "FINISHED" }

                        when {
                            tasksLoading -> Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) { CircularProgressIndicator() }

                            tasksError != null -> Text("Error loading tasks: $tasksError", color = Color.Red)
                            else -> {
                                Text(
                                    text = "TODO TASKS",
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1C1C1C)
                                )
                                Spacer(Modifier.height(20.dp))
                                Card(
                                    shape = RoundedCornerShape(16.dp),
                                    elevation = CardDefaults.cardElevation(6.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(6.dp)
                                        .clickable { navController.navigate(Screen.TaskScreen.route) },
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(text = "You have $toDoCount task${if (toDoCount == 1) "" else "s"} to do!"
                                            ,fontSize = 25.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF0D1A2B))

                                        Spacer(modifier = Modifier.height(4.dp))

                                        Text(
                                            text = "Take me there"
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "DAILY PICKS",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1C1C1C)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
                items(visibleItems.shuffled().take(4)) { item ->

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
                            child.childId?.let { id ->
                                // nbkidsViewModel.orderItem(id, item.globalItem.id)
                            }
                        }
                    )
                }
            }
        }
    }
}