package com.example.androidtemplate.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidtemplate.navigation.Screen
import com.example.androidtemplate.ui.composables.ZuzuBottomNavBar
import com.example.androidtemplate.viewmodels.LeaderboardViewModel
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import com.example.androidtemplate.viewmodels.TaskViewModel
import com.example.androidtemplate.viewmodels.WalletViewModel
import com.example.androidtemplate.R
import com.example.androidtemplate.ui.composables.Header
import com.example.androidtemplate.ui.composables.LeaderboardItem


@Composable
fun LeaderboardScreen(
    navController: NavController,
    nbkidsViewModel: NBKidsViewModel
) {
    val context = LocalContext.current
    val walletViewModel = remember { WalletViewModel(context) }
    val taskViewModel = remember { TaskViewModel(context) }
    val leaderboardViewModel = remember { LeaderboardViewModel(context) }

    var selectedTab by remember { mutableStateOf("Leaderboard") }
    val child = nbkidsViewModel.selectedChild
    val childId = (child?.childId ?: Long) as Long


    LaunchedEffect(child) {
            walletViewModel.fetchWallet(childId)
            taskViewModel.fetchTasks(childId)
            leaderboardViewModel.fetchLeaderboard()

    }

    val wallet by walletViewModel.walletState.collectAsState()
    val leaderboard = leaderboardViewModel.entries
    val leaderboardLoading = leaderboardViewModel.isLoading
    val leaderboardError = leaderboardViewModel.errorMessage

    if (child == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No child selected.", color = Color.Red)
        }
        return
    }

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

        Column(horizontalAlignment = Alignment.CenterHorizontally){
            if (child != null) {
                Header(child = child, wallet = wallet)
                Spacer(modifier = Modifier.height(16.dp))
            }


            LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = 24.dp,
                    end = 24.dp,
                    bottom = innerPadding.calculateBottomPadding()
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {

                if (leaderboardLoading) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                } else if (leaderboardError != null) {
                    item {
                        Text("Error loading leaderboard: $leaderboardError", color = Color.Red)
                    }
                } else {
                    if (leaderboard.size == 1) {
                        val podium = leaderboard.take(1)
                        // 1st with crown
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Box(contentAlignment = Alignment.TopCenter) {
                                        Image(
                                            painter = painterResource(id = getAvatarDrawable(podium[0].avatar)),
                                            contentDescription = podium[0].name,
                                            modifier = Modifier.size(90.dp).clip(CircleShape)
                                        )
                                        Image(
                                            painter = painterResource(id = R.drawable.crown),
                                            contentDescription = "Crown",
                                            modifier = Modifier.size(30.dp).offset(y = (-12).dp)
                                        )
                                    }
                                    Text(podium[0].name, fontWeight = FontWeight.SemiBold)
                                    Surface(
                                        color = Color(0xFFCECECE),
                                        shape = MaterialTheme.shapes.medium,
                                    ) {
                                        Text(
                                            "${podium[0].points}",
                                            fontWeight = FontWeight.Black,
                                            modifier = Modifier.padding(6.dp),
                                        )
                                    }
                                    Image(
                                        painter = painterResource(id = R.drawable.leaderboard_1st),
                                        contentDescription = "1st",
                                        modifier = Modifier.size(250.dp)
                                    )
                                }
                            }
                        }
                    }
                    if (leaderboard.size == 2) {
                        val podium = leaderboard.take(2)
                        // 1st with crown
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(30.dp),
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Image(
                                        painter = painterResource(id = getAvatarDrawable(podium[1].avatar)),
                                        contentDescription = podium[1].name,
                                        modifier = Modifier.size(80.dp).clip(CircleShape)
                                    )
                                    Text(podium[1].name, fontWeight = FontWeight.SemiBold)
                                    Surface(
                                        color = Color(0xFFCECECE),
                                        shape = MaterialTheme.shapes.medium
                                    ) {
                                        Text(
                                            "${podium[1].points}",
                                            fontWeight = FontWeight.Black,
                                            modifier = Modifier.padding(6.dp)
                                        )
                                    }
                                    Image(
                                        painter = painterResource(id = R.drawable.leaderboard_2nd),
                                        contentDescription = "2nd",
                                        modifier = Modifier.size(150.dp)
                                    )
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Box(contentAlignment = Alignment.TopCenter) {
                                        Image(
                                            painter = painterResource(id = getAvatarDrawable(podium[0].avatar)),
                                            contentDescription = podium[0].name,
                                            modifier = Modifier.size(90.dp).clip(CircleShape)
                                        )
                                        Image(
                                            painter = painterResource(id = R.drawable.crown),
                                            contentDescription = "Crown",
                                            modifier = Modifier.size(30.dp).offset(y = (-12).dp)
                                        )
                                    }
                                    Text(podium[0].name, fontWeight = FontWeight.SemiBold)
                                    Surface(
                                        color = Color(0xFFCECECE),
                                        shape = MaterialTheme.shapes.medium
                                    ) {
                                        Text(
                                            "${podium[0].points}",
                                            fontWeight = FontWeight.Black,
                                            modifier = Modifier.padding(6.dp)
                                        )
                                    }
                                    Image(
                                        painter = painterResource(id = R.drawable.leaderboard_1st),
                                        contentDescription = "1st",
                                        modifier = Modifier.size(200.dp)
                                    )
                                }
                            }
                        }
                    }
                    if (leaderboard.size >= 3) {
                        val podium = leaderboard.take(3)
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // 2nd
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Image(
                                        painter = painterResource(id = getAvatarDrawable(podium[1].avatar)),
                                        contentDescription = podium[1].name,
                                        modifier = Modifier.size(80.dp).clip(CircleShape)
                                    )
                                    Text(podium[1].name, fontWeight = FontWeight.SemiBold)
                                    Surface(
                                        color = Color(0xFFCECECE),
                                        shape = MaterialTheme.shapes.medium
                                    ) {
                                        Text(
                                            "${podium[1].points}",
                                            fontWeight = FontWeight.Black,
                                            modifier = Modifier.padding(6.dp)
                                        )
                                    }
                                    Image(
                                        painter = painterResource(id = R.drawable.leaderboard_2nd),
                                        contentDescription = "2nd",
                                        modifier = Modifier.size(120.dp)
                                    )
                                }

                                // 1st with crown
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Box(contentAlignment = Alignment.TopCenter) {
                                        Image(
                                            painter = painterResource(id = getAvatarDrawable(podium[0].avatar)),
                                            contentDescription = podium[0].name,
                                            modifier = Modifier.size(90.dp).clip(CircleShape)
                                        )
                                        Image(
                                            painter = painterResource(id = R.drawable.crown),
                                            contentDescription = "Crown",
                                            modifier = Modifier.size(30.dp).offset(y = (-12).dp)
                                        )
                                    }
                                    Text(podium[0].name, fontWeight = FontWeight.SemiBold)
                                    Surface(
                                        color = Color(0xFFCECECE),
                                        shape = MaterialTheme.shapes.medium
                                    ) {
                                        Text(
                                            "${podium[0].points}",
                                            fontWeight = FontWeight.Black,
                                            modifier = Modifier.padding(6.dp)
                                        )
                                    }
                                    Image(
                                        painter = painterResource(id = R.drawable.leaderboard_1st),
                                        contentDescription = "1st",
                                        modifier = Modifier.size(120.dp)
                                    )
                                }

                                // 3rd
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Image(
                                        painter = painterResource(id = getAvatarDrawable(podium[2].avatar)),
                                        contentDescription = podium[2].name,
                                        modifier = Modifier.size(80.dp).clip(CircleShape)
                                    )
                                    Text(podium[2].name, fontWeight = FontWeight.SemiBold)
                                    Surface(
                                        color = Color(0xFFCECECE),
                                        shape = MaterialTheme.shapes.medium
                                    ) {
                                        Text(
                                            "${podium[2].points}",
                                            fontWeight = FontWeight.Black,
                                            modifier = Modifier.padding(6.dp)
                                        )
                                    }
                                    Image(
                                        painter = painterResource(id = R.drawable.leaderboard_3rd),
                                        contentDescription = "3rd",
                                        modifier = Modifier.size(120.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Divider(
                                color = Color.LightGray,
                                thickness = 1.dp,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )

                        }

                    }
                    itemsIndexed(leaderboard) { index, entry ->
                        LeaderboardItem((index + 1), entry)
                    }
                }
            }
        }
    }
}