package com.example.androidtemplate.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.androidtemplate.ui.composables.FancyTaskCard
import com.example.androidtemplate.viewmodels.CardScreenViewModel
import com.example.androidtemplate.viewmodels.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentCheckTaskScreen(
    navController: NavController,
    cardViewModel: CardScreenViewModel,
    taskViewModel: TaskViewModel
) {
    val context = LocalContext.current
    val selectedCard by cardViewModel.selectedCard.collectAsState()
    val childId = selectedCard?.childId

    var selectedFilter by remember { mutableStateOf("INCOMPLETE") }

    val tasks by remember { derivedStateOf { taskViewModel.tasks } }
    val isLoading = taskViewModel.isLoading
    val errorMessage = taskViewModel.errorMessage

    LaunchedEffect(childId) {
        if (childId != null) {
            taskViewModel.fetchTasks(childId)
        }
    }

    val filteredTasks = when (selectedFilter) {
        "COMPLETED" -> tasks.filter { it.status == "FINISHED" }
        else -> tasks.filter { it.status != "FINISHED" }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Child Tasks", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { selectedFilter = "INCOMPLETE" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedFilter == "INCOMPLETE") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Incomplete Tasks")
                }
                Button(
                    onClick = { selectedFilter = "COMPLETED" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedFilter == "COMPLETED") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Completed Tasks")
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
                                    if (selectedFilter == "INCOMPLETE") {
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