package com.example.androidtemplate.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.androidtemplate.ui.composables.FancyTaskCard
import com.example.androidtemplate.ui.composables.Header
import com.example.androidtemplate.ui.composables.TypingText
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


            val toDoTasks = tasks.filter { it.status != "FINISHED" }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            when {
                tasksLoading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
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
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(40.dp))
                            TypingText("No Tasks assigned.", color = Color(0xFF848484), fontWeight = FontWeight.Normal, fontSize = 20.sp)
                        }                    }
                }

                else -> {
                    items(toDoTasks) { task ->
                        FancyTaskCard(
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
}