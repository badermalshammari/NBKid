package com.example.androidtemplate.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.androidtemplate.navigation.Screen
import com.example.androidtemplate.ui.composables.CreditCardComposable
import com.example.androidtemplate.ui.composables.FancyTaskCard
import com.example.androidtemplate.viewmodels.CardScreenViewModel
import com.example.androidtemplate.viewmodels.TaskViewModel
import com.example.androidtemplate.viewmodels.WalletViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentCheckTaskScreen(
    navController: NavController,
    cardViewModel: CardScreenViewModel,
    taskViewModel: TaskViewModel,
    walletViewModel: WalletViewModel
) {
    val context = LocalContext.current
    val selectedCard by cardViewModel.selectedCard.collectAsState()
    val childId = selectedCard?.childId

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Incomplete", "Completed")

    val tasks by remember { derivedStateOf { taskViewModel.tasks } }
    val isLoading = taskViewModel.isLoading
    val errorMessage = taskViewModel.errorMessage

    LaunchedEffect(childId) {
        if (childId != null) {
            taskViewModel.fetchTasks(childId)
            walletViewModel.fetchWallet(childId)
        }
    }

    val filteredTasks = when (selectedTabIndex) {
        1 -> tasks.filter { it.status == "FINISHED" }
        else -> tasks.filter { it.status != "FINISHED" }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = selectedCard?.cardHolderName ?: "No Name", fontWeight = FontWeight.Bold)
                        Text("(${selectedCard?.accountNumber})", style = MaterialTheme.typography.labelSmall)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        Toast.makeText(context, "Refreshing...", Toast.LENGTH_SHORT).show()
                        navController.navigate(Screen.ParentCheckTask.route)

                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.6f)
                    .align(Alignment.CenterHorizontally)
                    .shadow(10.dp, shape = RoundedCornerShape(20.dp))
            ) {
                CreditCardComposable(selectedCard)
            }

            TabRow(selectedTabIndex = selectedTabIndex) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }

            when {
                isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                !errorMessage.isNullOrEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(errorMessage, color = MaterialTheme.colorScheme.error)
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(filteredTasks) { task ->
                            FancyTaskCard(
                                title = task.title,
                                points = task.points ?: 0,
                                gems = task.gems,
                                onClick = {
                                    if (selectedTabIndex == 0) {
                                        val childIdValue = selectedCard?.childId ?: return@FancyTaskCard
                                        taskViewModel.completeTask(
                                            childId = childIdValue,
                                            taskId = task.taskId,
                                            onSuccess = {
                                                Toast.makeText(context, "Marked as done", Toast.LENGTH_SHORT).show()
                                                taskViewModel.fetchTasks(childIdValue)
                                            },
                                            onError = {
                                                Toast.makeText(context, "Error: $it", Toast.LENGTH_SHORT).show()
                                            }
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}