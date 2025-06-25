package com.example.androidtemplate.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.NavController
import com.example.androidtemplate.R
import com.example.androidtemplate.navigation.Screen
import com.example.androidtemplate.ui.composables.ZuzuBottomNavBar
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import com.example.androidtemplate.viewmodels.TaskViewModel
import com.example.androidtemplate.viewmodels.WalletViewModel

@Composable
fun TaskScreen(
    nbkidsViewModel: NBKidsViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val walletViewModel = remember { WalletViewModel(context) }
    val taskViewModel = remember { TaskViewModel(context) }

    var selectedTab by remember { mutableStateOf("Tasks") }

    val child = nbkidsViewModel.selectedChild

    LaunchedEffect(child) {
        child?.childId?.let {
            walletViewModel.fetchWallet(it)
            taskViewModel.fetchTasks(it)
        }
    }

    val tasks = taskViewModel.tasks
    val tasksLoading = taskViewModel.isLoading
    val tasksError = taskViewModel.errorMessage

    val wallet by walletViewModel.walletState.collectAsState()
    val isLoading by walletViewModel.isLoading.collectAsState()
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
            item {
                // Welcome + Avatar + Gems/Points
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(horizontalAlignment = Alignment.Start) {
                        Text("Welcome", fontSize = 20.sp, color = Color.Black)
                        Text(text = "${child.name}..", fontSize = 28.sp, fontWeight = FontWeight.Bold)
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
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Column {
                                Text("Available Gems", color = Color.Gray, fontSize = 14.sp)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(painter = painterResource(id = com.example.androidtemplate.R.drawable.gems), contentDescription = "Gems", modifier = Modifier.size(24.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("${wallet?.gems ?: 0}", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                                }
                                Text("= ${"%.3f".format((wallet?.gems ?: 0) / 1000.0)} KD", fontSize = 10.sp, color = Color.Gray)
                            }
                            Column {
                                Text("Points", color = Color.Gray, fontSize = 14.sp)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(painter = painterResource(id = R.drawable.points), contentDescription = "Points", modifier = Modifier.size(24.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("${wallet?.pointsBalance ?: 0}", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

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

                Text("To Do Tasks", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }

            when {
                tasksLoading -> {
                    item {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                }

                tasksError != null -> {
                    item {
                        Text("Error loading tasks: $tasksError", color = Color.Red)
                    }
                }

                tasks.isEmpty() -> {
                    item {
                        Text("No tasks assigned.", color = Color.Gray)
                    }
                }

                else -> {
                    items(tasks) { task ->
                        com.example.androidtemplate.ui.composables.FancyTaskCard(
                            title = task.title,
                            points = task.points ?: 0,
                            gems = task.gems,
                            progressPercent = when (task.type.uppercase()) {
                                "VIDEO", "QUIZ" -> 87
                                else -> null
                            },
                            onClick = {
                                nbkidsViewModel.selectedTask = task
                                navController.navigate(Screen.TaskDetail.route)
                            }
                        )
                    }
                }
            }
        }
    }
}