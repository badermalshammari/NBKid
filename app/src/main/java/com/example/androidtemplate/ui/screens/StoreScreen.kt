package com.example.androidtemplate.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.example.androidtemplate.ui.composables.Header
import com.example.androidtemplate.ui.composables.LoadingIndicator
import com.example.androidtemplate.ui.composables.StoreItemCard
import com.example.androidtemplate.ui.composables.ZuzuBottomNavBar
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import com.example.androidtemplate.viewmodels.TaskViewModel
import com.example.androidtemplate.viewmodels.WalletViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

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

    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(child) {
        child?.childId?.let {
            nbkidsViewModel.fetchStoreItems(it)
            walletViewModel.fetchWallet(it)
            taskViewModel.fetchTasks(it)
        }
    }

    if (nbkidsViewModel.isLoading) {
        LoadingIndicator()
        return
    }

    val wallet by walletViewModel.walletState.collectAsState()
    val isLoading by walletViewModel.isLoading.collectAsState()
    val storeItems by nbkidsViewModel.storeitems

    Scaffold(
        bottomBar = {
            ZuzuBottomNavBar(
                navController = navController, selected = selectedTab,
                onItemSelected = { item ->
                    selectedTab = item
                    when (item) {
                        "Orders" -> navController.navigate(Screen.OrdersScreen.route)
                        "Tasks" -> navController.navigate(Screen.TaskScreen.route)
                        "Store" -> navController.navigate(Screen.StoreScreen.route)
                        "Leaderboard" -> navController.navigate(Screen.LeaderboardScreen.route)
                    }
                })
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
                }
                isRefreshing = false
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                if (child != null) {
                    Header(child = child, wallet = wallet)
                    Spacer(modifier = Modifier.height(16.dp))
                }


            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {

                items(visibleItems) { item ->
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
                        walletViewModel = walletViewModel,
                        child = child
                    )
                }
            }
            }
        }
    }
}