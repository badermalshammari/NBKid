package com.example.androidtemplate.ui.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
//
//@Composable
//fun ToDoTasksScreen(
//    modifier: Modifier = Modifier,
//    showTaskDetail: Boolean = false,
//    onTaskClick: (Task) -> Unit = {},
//    onBackClick: () -> Unit = {}
//) {
//    if (showTaskDetail) {
//        TaskDetailScreen(onBackClick = onBackClick)
//    } else {
//        TaskListScreen(
//            modifier = modifier,
//            onTaskClick = onTaskClick
//        )
//    }
//}
//
//@Composable
//fun TaskListScreen(
//    modifier: Modifier = Modifier,
//    onTaskClick: (Task) -> Unit = {}
//) {
//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        // Header Section
//        TasksHeader()
//
//        // Tasks Content
//        TasksContent(
//            modifier = Modifier.weight(1f),
//            onTaskClick = onTaskClick
//        )
//
//        // Bottom Navigation
//        BottomNavigationBar()
//    }
//}
//
//@Composable
//fun TaskDetailScreen(
//    onBackClick: () -> Unit = {}
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        // Header Section
//        TasksHeader()
//
//        // Task Detail Content
//        TaskDetailContent(
//            modifier = Modifier.weight(1f),
//            onBackClick = onBackClick
//        )
//
//        // Bottom Navigation
//        BottomNavigationBar()
//    }
//}
//
//@Composable
//fun TasksHeader() {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(20.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        // User Avatar
//        Box(
//            modifier = Modifier
//                .size(80.dp)
//                .clip(CircleShape)
//                .background(Color(0xFFF0F0F0)),
//            contentAlignment = Alignment.Center
//        ) {
//            // User avatar - replace with actual image
//            Text("👦", fontSize = 40.sp)
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Gems and Points Row
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            // Available Gems
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Text(
//                    "Available Gems",
//                    style = MaterialTheme.typography.bodySmall,
//                    color = Color.Gray
//                )
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Box(
//                        modifier = Modifier
//                            .size(16.dp)
//                            .background(Color(0xFF10B981), CircleShape)
//                    )
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Text(
//                        "3000",
//                        style = MaterialTheme.typography.titleMedium,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
//                Text(
//                    "+ 3,000 KD",
//                    style = MaterialTheme.typography.bodySmall,
//                    color = Color.Gray
//                )
//            }
//
//            // Points
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Text(
//                    "Points",
//                    style = MaterialTheme.typography.bodySmall,
//                    color = Color.Gray
//                )
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Box(
//                        modifier = Modifier
//                            .size(16.dp)
//                            .background(Color(0xFFFFA500), CircleShape)
//                    )
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Text(
//                        "48307",
//                        style = MaterialTheme.typography.titleMedium,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun TasksContent(
//    modifier: Modifier = Modifier,
//    onTaskClick: (Task) -> Unit = {}
//) {
//    Column(
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(horizontal = 20.dp)
//    ) {
//        // Gradient divider
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(4.dp)
//                .background(
//                    brush = Brush.horizontalGradient(
//                        colors = listOf(
//                            Color(0xFFFF6B35),
//                            Color(0xFFFFA500),
//                            Color(0xFFFFD700)
//                        )
//                    )
//                )
//        )
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // In Progress/Done Toggle
//        Row(
//            horizontalArrangement = Arrangement.spacedBy(0.dp)
//        ) {
//            ToggleButton1(
//                text = "In Progress",
//                isSelected = true,
//                onClick = { }
//            )
//            ToggleButton1(
//                text = "Done",
//                isSelected = false,
//                onClick = { }
//            )
//        }
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        // To Do Tasks Title
//        Text(
//            "To Do Tasks",
//            style = MaterialTheme.typography.headlineMedium,
//            fontWeight = FontWeight.Bold
//        )
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        // Task Cards
//        TasksList(onTaskClick = onTaskClick)
//    }
//}
//
//@Composable
//fun TaskDetailContent(
//    modifier: Modifier = Modifier,
//    onBackClick: () -> Unit = {}
//) {
//    Column(
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(horizontal = 20.dp)
//    ) {
//        // Gradient divider
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(4.dp)
//                .background(
//                    brush = Brush.horizontalGradient(
//                        colors = listOf(
//                            Color(0xFFFF6B35),
//                            Color(0xFFFFA500),
//                            Color(0xFFFFD700)
//                        )
//                    )
//                )
//        )
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // In Progress/Done Toggle
////        Row(
////            horizontalArrangement = Arrangement.spacedBy(0.dp)
////        ) {
////            ToggleButton1(
////                text = "In Progress",
////                isSelected = true,
////                onClick = { }
////            )
////            ToggleButton1(
////                text = "Done",
////                isSelected = false,
////                onClick = { }
////            )
////        }
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        // To Do Tasks Title with Back Button
//        Row(
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            IconButton(
//                onClick = onBackClick,
//                modifier = Modifier.size(40.dp)
//            ) {
//                Box(
//                    modifier = Modifier
//                        .size(32.dp)
//                        .background(Color(0xFFFF6B35), CircleShape),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.ArrowBack,
//                        contentDescription = "Back",
//                        tint = Color.White,
//                        modifier = Modifier.size(18.dp)
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.width(12.dp))
//
//            Text(
//                "To Do Tasks",
//                style = MaterialTheme.typography.headlineMedium,
//                fontWeight = FontWeight.Bold
//            )
//        }
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        // Task Detail Card
//        TaskDetailCard()
//    }
//}
//
//@Composable
//fun TasksList(onTaskClick: (Task) -> Unit = {}) {
//    val tasks = listOf(
//        Task(
//            id = 1,
//            name = "Clean Your Room",
//            coins = 50,
//            gems = 1000,
//            progress = null
//        ),
//        Task(
//            id = 2,
//            name = "Watch Chapter 8 from math",
//            coins = 100,
//            gems = 3000,
//            progress = 87
//        )
//    )
//
//    LazyColumn(
//        verticalArrangement = Arrangement.spacedBy(16.dp)
//    ) {
//        items(tasks) { task ->
//            TaskCard(
//                task = task,
//                onClick = { onTaskClick(task) }
//            )
//        }
//    }
//}
//
//@Composable
//fun TaskCard(
//    task: Task,
//    onClick: () -> Unit = {}
//) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { onClick() },
//        colors = CardDefaults.cardColors(
//            containerColor = Color(0xFFF5F5F5)
//        ),
//        shape = RoundedCornerShape(16.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(20.dp)
//        ) {
//            Text(
//                "Task Name",
//                style = MaterialTheme.typography.bodySmall,
//                color = Color.Gray
//            )
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            Text(
//                task.name,
//                style = MaterialTheme.typography.titleLarge,
//                fontWeight = FontWeight.Bold,
//                color = Color(0xFF1E40AF)
//            )
//
//            // Progress bar if task has progress
//            task.progress?.let { progress ->
//                Spacer(modifier = Modifier.height(16.dp))
//
//                LinearProgressIndicator(
//                    progress = progress / 100f,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(8.dp)
//                        .clip(RoundedCornerShape(4.dp)),
//                    color = Color(0xFF6B7280),
//                    trackColor = Color(0xFFE5E7EB)
//                )
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                Text(
//                    "$progress%",
//                    style = MaterialTheme.typography.bodyMedium,
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF6B7280)
//                )
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Rewards section
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.End
//            ) {
//                Column(
//                    horizontalAlignment = Alignment.End
//                ) {
//                    // Coins
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .size(16.dp)
//                                .background(Color(0xFFFFA500), CircleShape)
//                        )
//                        Spacer(modifier = Modifier.width(4.dp))
//                        Text(
//                            task.coins.toString(),
//                            style = MaterialTheme.typography.titleMedium,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    // Gems
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .size(16.dp)
//                                .background(Color(0xFF10B981), CircleShape)
//                        )
//                        Spacer(modifier = Modifier.width(4.dp))
//                        Text(
//                            task.gems.toString(),
//                            style = MaterialTheme.typography.titleMedium,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun TaskDetailCard() {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        colors = CardDefaults.cardColors(
//            containerColor = Color(0xFFF5F5F5)
//        ),
//        shape = RoundedCornerShape(16.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(20.dp)
//        ) {
//            Text(
//                "Task Name",
//                style = MaterialTheme.typography.bodySmall,
//                color = Color.Gray
//            )
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            Text(
//                "Clean Your Room",
//                style = MaterialTheme.typography.titleLarge,
//                fontWeight = FontWeight.Bold,
//                color = Color(0xFF1E40AF)
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Rewards section
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.End
//            ) {
//                Column(
//                    horizontalAlignment = Alignment.End
//                ) {
//                    // Coins
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .size(16.dp)
//                                .background(Color(0xFFFFA500), CircleShape)
//                        )
//                        Spacer(modifier = Modifier.width(4.dp))
//                        Text(
//                            "50",
//                            style = MaterialTheme.typography.titleMedium,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    // Gems
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .size(16.dp)
//                                .background(Color(0xFF10B981), CircleShape)
//                        )
//                        Spacer(modifier = Modifier.width(4.dp))
//                        Text(
//                            "1000",
//                            style = MaterialTheme.typography.titleMedium,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//                }
//            }
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Large instruction text
//            Text(
//                "START CLEANING\nUR ROOM NOW!\nAND LET ME KNOW\nWHEN UR FINISH",
//                style = MaterialTheme.typography.headlineSmall,
//                fontWeight = FontWeight.Bold,
//                color = Color(0xFF1E40AF),
//                textAlign = TextAlign.Center,
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
//    }
//}
//
////@Composable
////fun ToggleButton1(
////    text: String,
////    isSelected: Boolean,
////    onClick: () -> Unit
////) {
////    Box(
////        modifier = Modifier
////            .clickable { onClick() }
////            .background(
////                color = if (isSelected) Color(0xFF1E40AF) else Color.Transparent,
////                shape = RoundedCornerShape(20.dp)
////            )
////            .padding(horizontal = 16.dp, vertical = 8.dp)
////    ) {
////        Text(
////            text = text,
////            color = if (isSelected) Color.White else Color.Gray,
////            style = MaterialTheme.typography.bodyMedium,
////            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
////        )
////    }
////}
//
//@Composable
//fun BottomNavigationBar() {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(60.dp)
//            .background(
//                brush = Brush.horizontalGradient(
//                    colors = listOf(
//                        Color(0xFF8B5CF6),
//                        Color(0xFFEF4444)
//                    )
//                )
//            ),
//        contentAlignment = Alignment.Center
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            BottomNavItem("💬", false)
//            BottomNavItem("📊", false)
//            BottomNavItem("🏆", true)
//            BottomNavItem("❤️", false)
//            BottomNavItem("📋", false)
//        }
//    }
//}
//
////@Composable
////fun BottomNavItem(icon: String, isSelected: Boolean) {
////    Box(
////        modifier = Modifier
////            .size(40.dp)
////            .background(
////                if (isSelected) Color.White.copy(alpha = 0.2f) else Color.Transparent,
////                CircleShape
////            ),
////        contentAlignment = Alignment.Center
////    ) {
////        Text(
////            icon,
////            fontSize = 20.sp,
////            color = Color.White
////        )
////    }
////}
//
//// Data class for Task
//data class Task(
//    val id: Int,
//    val name: String,
//    val coins: Int,
//    val gems: Int,
//    val progress: Int? = null
//)
//
//@Preview(showBackground = true)
//@Composable
//fun ToDoTasksScreenPreview() {
//    MaterialTheme {
//        ToDoTasksScreen()
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun TaskDetailScreenPreview() {
//    MaterialTheme {
//        ToDoTasksScreen(showTaskDetail = true)
//    }
//}