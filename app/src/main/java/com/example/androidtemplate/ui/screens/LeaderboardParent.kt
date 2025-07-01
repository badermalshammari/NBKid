package com.example.androidtemplate.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidtemplate.R
import com.example.androidtemplate.ui.composables.CreditCardComposable
import com.example.androidtemplate.ui.composables.Header
import com.example.androidtemplate.ui.composables.LeaderboardItem
import com.example.androidtemplate.viewmodels.*
import com.example.androidtemplate.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardParent(
    cardId: Long,
    cardViewModel: CardScreenViewModel,
    leaderboardViewModel: LeaderboardViewModel,
    navController: NavController) {
    val cards by cardViewModel.cards.collectAsState()
    val card = cards.find { it.cardId == cardId }

    val leaderboard = leaderboardViewModel.entries
    val leaderboardLoading = leaderboardViewModel.isLoading
    val leaderboardError = leaderboardViewModel.errorMessage

    LaunchedEffect(cardId) {
        leaderboardViewModel.fetchLeaderboard()
    }

    if (card == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Card not found", color = MaterialTheme.colorScheme.error)
        }
        return
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = card.cardHolderName ?: "No Name", fontWeight = FontWeight.Bold)
                        Text("(${card.accountNumber})", style = MaterialTheme.typography.labelSmall)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
                CreditCardComposable(card)
            }

            Spacer(Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                item {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text(
                            "Leaderboard",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
                when {
                    leaderboardLoading -> {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    leaderboardError != null -> {
                        item {
                            Text("Error loading leaderboard: $leaderboardError", color = Color.Red)
                        }
                    }
                    else -> {
                        val podium = leaderboard.take(3)
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // 2nd
                                if (podium.size >= 2) {
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
                                }

                                // 1st with crown
                                if (podium.isNotEmpty()) {
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
                                }

                                // 3rd
                                if (podium.size >= 3) {
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
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Divider(
                                color = Color.LightGray,
                                thickness = 1.dp,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }

                        itemsIndexed(leaderboard) { index, entry ->
                            LeaderboardItem(index + 1, entry)
                        }
                    }
                }
            }
        }
    }
}