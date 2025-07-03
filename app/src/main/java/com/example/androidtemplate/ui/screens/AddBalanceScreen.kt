package com.example.androidtemplate.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.androidtemplate.R
import com.example.androidtemplate.navigation.Screen
import com.example.androidtemplate.ui.composables.BalanceInfoComposable
import com.example.androidtemplate.ui.composables.CreditCardComposable
import com.example.androidtemplate.ui.composables.FloatUp
import com.example.androidtemplate.viewmodels.CardScreenViewModel
import com.example.androidtemplate.viewmodels.WalletViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBalanceScreen(
    navController: NavController,
    walletViewModel: WalletViewModel,
    cardViewModel: CardScreenViewModel,
    childId: Long
) {
    val context = LocalContext.current
    var amount by remember { mutableStateOf("") }

    val wallet by walletViewModel.walletState.collectAsState()
    val selectedCard by cardViewModel.selectedCard.collectAsState()
    val cards by cardViewModel.cards.collectAsState()

    LaunchedEffect(childId, cards) {
        walletViewModel.fetchWallet(childId)
        cards.find { it.childId == childId }?.let {
            cardViewModel.selectCard(it)
        }
    }

    val availableBalance = selectedCard?.balance?.toPlainString()?.let { " $it" } ?: " 0.000"
    val availableGems = wallet?.gems?.toString() ?: "0"
    val points = wallet?.pointsBalance?.toString() ?: "0"

    val gemIcon = painterResource(id = R.drawable.gems)

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
                        navController.navigate(Screen.AddBalanceScreen.route)
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                FloatUp {
                    Image(
                        painter = gemIcon,
                        contentDescription = "Gem Icon",
                        modifier = Modifier.size(100.dp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                BalanceInfoComposable(
                    availableBalance = availableBalance,
                    availableGems = availableGems,
                    points = points
                )

                Spacer(modifier = Modifier.height(32.dp))

                TextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Insert the Amount in Kuwaiti Dinar") },
                    modifier = Modifier
                        .size(width = 300.dp, height = 50.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF3875A7),
                        unfocusedBorderColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        cursorColor = Color.Black
                    ),
                    shape = RoundedCornerShape(50.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                Box(
                    modifier = Modifier
                        .size(width = 300.dp, height = 50.dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFF8E2DE2), Color(0xFFF27121))
                            ),
                            shape = RoundedCornerShape(50)
                        )
                        .clickable {
                            val enteredAmount = amount.toDoubleOrNull()
                            if (enteredAmount == null || enteredAmount <= 0.0) {
                                Toast.makeText(context, "Enter a valid amount", Toast.LENGTH_SHORT).show()
                                return@clickable
                            }


                            val gems = (enteredAmount * 1000).toInt()

                            walletViewModel.addGemsToChild(childId, gems) { success ->
                                if (success) {
                                    Toast.makeText(context, "Gems added successfully!", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                } else {
                                    Toast.makeText(context, "Insufficient balance or error occurred", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Send",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}