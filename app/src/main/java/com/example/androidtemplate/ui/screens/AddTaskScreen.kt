package com.example.androidtemplate.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidtemplate.R
import com.example.androidtemplate.data.mockVideoList
import com.example.androidtemplate.data.VideoOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    onBackClick: () -> Unit = {}
) {
    // Dummy data
    val accountName = "Khaled Account"
    val accountNumber = "12345678910"
    val availableBalance = "299.230 KD"
    val availableGems = "3000"
    val points = "48307"

    val todoIcon = painterResource(id = R.drawable.todolist)
    val videoIcon = painterResource(id = R.drawable.transfaremoney)

    // State
    var selectedTaskType by remember { mutableStateOf("Task ToDo") }
    var task by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var gems by remember { mutableStateOf("") }

    val videoOptions = mockVideoList
    var selectedVideo by remember { mutableStateOf<VideoOption?>(null) }
    var videoDropdownExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Task") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Account Header
            Text(text = accountName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = "($accountNumber)", fontSize = 14.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(16.dp))

            // Balance Info
            BalanceTaskInfoComposable(
                availableBalance = availableBalance,
                availableGems = availableGems,
                points = points
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Task Type Selection
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TaskTypeOption(
                    icon = todoIcon,
                    label = "Task ToDo",
                    selected = selectedTaskType == "Task ToDo",
                    onClick = {
                        selectedTaskType = "Task ToDo"
                        task = ""
                        description = ""
                        selectedVideo = null
                    }
                )
                TaskTypeOption(
                    icon = videoIcon,
                    label = "Video",
                    selected = selectedTaskType == "Video",
                    onClick = {
                        selectedTaskType = "Video"
                        task = ""
                        description = ""
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Inputs
            LabeledInput("Task", task) { task = it }
            LabeledInput("Description", description) { description = it }
            LabeledInput("Gems", gems) { gems = it }

            // Dropdown if type = Video
            if (selectedTaskType == "Video") {
                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = videoDropdownExpanded,
                    onExpandedChange = { videoDropdownExpanded = !videoDropdownExpanded }
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = selectedVideo?.title ?: "Select Video",
                        onValueChange = {},
                        label = { Text("Select Video") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = videoDropdownExpanded)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = videoDropdownExpanded,
                        onDismissRequest = { videoDropdownExpanded = false }
                    ) {
                        videoOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option.title) },
                                onClick = {
                                    selectedVideo = option
                                    task = option.title
                                    description = option.description
                                    videoDropdownExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            GradientSendButton {
                // send logic here
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun TaskTypeOption(
    icon: Painter,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Image(
            painter = icon,
            contentDescription = label,
            modifier = Modifier
                .size(50.dp)
                .padding(4.dp)
        )
        Text(
            text = label,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = if (selected) Color(0xFF3F51B5) else Color.Gray
        )
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF3F51B5))
        )
    }
}

@Composable
fun BalanceTaskInfoComposable(
    availableBalance: String,
    availableGems: String,
    points: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(text = "Available Balance", fontSize = 12.sp, color = Color.Gray)
            Text(text = availableBalance, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Available Gems", fontSize = 12.sp, color = Color.Gray)
            Text(text = availableGems, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = "= 3.000 KD", fontSize = 12.sp, color = Color.Gray)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(text = "Points", fontSize = 12.sp, color = Color.Gray)
            Text(text = points, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun LabeledInput(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    )
}

@Composable
fun GradientSendButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF3F51B5), Color(0xFF2196F3))
                )
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Send", color = Color.White, fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddTaskScreen() {
    AddTaskScreen()
}