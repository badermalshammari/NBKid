package com.example.androidtemplate
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Scaffold
//import androidx.compose.ui.Modifier
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.compose.rememberNavController
//import com.example.androidtemplate.navigation.AppNavigation
//import com.example.androidtemplate.ui.theme.AndroidTemplateTheme
//import com.example.androidtemplate.utils.AppInitializer
//import com.example.androidtemplate.viewmodels.AuthViewModel
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setContent {
//            AndroidTemplateTheme {
//                val navController = rememberNavController()
//
//                val authViewModel: AuthViewModel = viewModel(
//                    factory = AppInitializer.provideAuthViewModelFactory(applicationContext)
//                )
//
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Box(modifier = Modifier.padding(innerPadding)) {
//                        AppNavigation(navController = navController, authViewModel = authViewModel)
//                    }
//                }
//            }
//        }
//    }
//}

//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.androidtemplate.ui.theme.AndroidTemplateTheme
//import com.example.androidtemplate.ui.composables.ProductCard
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            AndroidTemplateTheme {
//                Surface(modifier = Modifier.fillMaxSize()) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(vertical = 16.dp)
//                    ) {
//                        ProductCard(
//                            productTitle = "The Full Closet",
//                            productSubtitle = "Boardgame",
//                            price = 3500,
//                            productImage = {
//                                Box(
//                                    modifier = Modifier
//                                        .fillMaxSize()
//                                        .background(Color(0xFF6366F1), RoundedCornerShape(8.dp)),
//                                    contentAlignment = Alignment.Center
//                                ) {
//                                    Text("📦", fontSize = 32.sp)
//                                }
//                            },
//                            onGetItClick = { /* Handle click */ }
//                        )
//
//                        ProductCard(
//                            productTitle = "EggEggy Wawa",
//                            productSubtitle = "Mystery Gift",
//                            price = 1500,
//                            productImage = {
//                                Box(
//                                    modifier = Modifier
//                                        .fillMaxSize()
//                                        .background(Color(0xFF06B6D4), RoundedCornerShape(8.dp)),
//                                    contentAlignment = Alignment.Center
//                                ) {
//                                    Text("🥚", fontSize = 32.sp)
//                                }
//                            },
//                            onGetItClick = { /* Handle click */ }
//                        )
//
//                        ProductCard(
//                            productTitle = "Dog Car",
//                            productSubtitle = "Car",
//                            price = 2000,
//                            productImage = {
//                                Box(
//                                    modifier = Modifier
//                                        .fillMaxSize()
//                                        .background(Color(0xFFEF4444), RoundedCornerShape(8.dp)),
//                                    contentAlignment = Alignment.Center
//                                ) {
//                                    Text("🚗", fontSize = 32.sp)
//                                }
//                            },
//                            onGetItClick = { /* Handle click */ }
//                        )
//                    }
//                }
//            }
//        }
//    }
//}


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.androidtemplate.ui.composables.DashboardActionButton
import com.example.androidtemplate.ui.composables.TaskCard
import com.example.androidtemplate.ui.theme.AndroidTemplateTheme
import com.example.androidtemplate.ui.composables.ProductCard
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import com.example.androidtemplate.ui.screens.LeaderboardScreen
//import com.example.androidtemplate.ui.screens.ToDoTasksScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidTemplateTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    //SampleTaskList()
                    //DashboardActionsGrid()
                    LeaderboardScreen()
                    //ToDoTasksScreen()
//                    ProductCard(
//                        imageUrl = "https://via.placeholder.com/150",
//                        productName = "Wireless Mouse",
//                        productType = "Electronics",
//                        price = 25,
//                        onClick = { /* Handle click */ },
//                        modifier = Modifier.padding(16.dp)
//                    )
                }
            }
        }
    }
}

//@Composable
//fun SampleTaskList() {
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(vertical = 16.dp)
//    ) {
//        item {
//            TaskCard(
//                taskTitle = "Clean Your Room",
//                coinReward = 50,
//                gemReward = 1000
//            )
//        }
//        item {
//            TaskCard(
//                taskTitle = "Watch Chapter 8 from Math",
//                taskSubtitle = "Watch the full lecture and answer the quiz",
//                coinReward = 100,
//                gemReward = 3000,
//                progress = 87
//            )
//        }
//        item {
//            TaskCard(
//                taskTitle = "Watch Chapter 7 from Math",
//                coinReward = 100,
//                gemReward = 3000,
//                progress = 100,
//                backgroundColor = Color(0xFFE6F4ED) // Light green
//            )
//        }
//    }
//}
//
//
//@Composable
//fun DashboardActionsGrid() {
//    Column(modifier = Modifier.padding(16.dp)) {
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
//            DashboardActionButton(
//                icon = Icons.Default.Add,
//                backgroundColor = Color(0xFFFFC107),
//                label = "Add Balance"
//            ) { /* Handle click */ }
//
//            DashboardActionButton(
//                icon = Icons.Default.Settings,
//                backgroundColor = Color(0xFFFF5722),
//                label = "Settings"
//            ) { /* Handle click */ }
//        }
//
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
//            DashboardActionButton(
//                icon = Icons.Default.Send,
//                backgroundColor = Color(0xFF9C27B0),
//                iconTint = Color(0xFF1DE9B6),
//                label = "Transfer"
//            ) { /* Handle click */ }
//
//            DashboardActionButton(
//                icon = Icons.Default.Leaderboard,
//                backgroundColor = Color(0xFF9C27B0),
//                iconTint = Color(0xFFFFEB3B),
//                label = "Leaderboard"
//            ) { /* Handle click */ }
//        }
//
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
//            DashboardActionButton(
//                icon = Icons.Default.Edit,
//                backgroundColor = Color(0xFFAB47BC),
//                iconTint = Color(0xFFFF7043),
//                label = "Add Task"
//            ) { /* Handle click */ }
//
//            DashboardActionButton(
//                icon = Icons.Default.CardGiftcard,
//                backgroundColor = Color(0xFF9C27B0),
//                iconTint = Color(0xFF00E676),
//                label = "Gifts"
//            ) { /* Handle click */ }
//        }
//    }
//}

//
//@Composable
//fun SampleTaskList() {
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(vertical = 16.dp)
//    ) {
//        item {
//            TaskCard(
//                taskTitle = "Clean Your Room",
//                coinReward = 50,
//                gemReward = 1000
//            )
//        }
//        item {
//            TaskCard(
//                taskTitle = "Watch Chapter 8 from Math",
//                taskSubtitle = "Watch the full lecture and answer the quiz",
//                coinReward = 100,
//                gemReward = 3000,
//                progress = 87
//            )
//        }
//        item {
//            TaskCard(
//                taskTitle = "Watch Chapter 7 from Math",
//                taskSubtitle = "Revise key concepts before the test",
//                coinReward = 100,
//                gemReward = 3000,
//                progress = 100,
//                backgroundColor = Color(0xFFE6F4ED) // Light green
//            )
//        }
//    }
//}
