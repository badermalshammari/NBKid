package com.example.androidtemplate.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidtemplate.R
import com.example.androidtemplate.ui.composables.BalanceInfoComposable
import com.example.androidtemplate.ui.composables.SendButton
import com.example.androidtemplate.viewmodels.WalletViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBalanceScreen(    navController: NavController,
                         walletViewModel: WalletViewModel,
                         childId: Long) {
    val context = LocalContext.current

    var amount by remember { mutableStateOf("") }

    val availableBalance = "299.230 KD"
    val availableGems = "3000"
    val points = "48307"
    val gemIcon = painterResource(id = R.drawable.gems)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Gems") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                modifier = Modifier.background(Color.White)
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                // Gems icon centered
                Image(
                    painter = gemIcon,
                    contentDescription = "Gem Icon",
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Balance info
                BalanceInfoComposable(
                    availableBalance = availableBalance,
                    availableGems = availableGems,
                    points = points
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Amount input field
                TextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                        .padding(4.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Send button
                SendButton(onClick = {
                    val enteredAmount = amount.toDoubleOrNull()
                    if (enteredAmount == null || enteredAmount <= 0.0) {
                        Toast.makeText(context, "Enter a valid amount", Toast.LENGTH_SHORT).show()
                        return@SendButton
                    }

                    val gems = (enteredAmount * 1000).toInt()

                    walletViewModel.addGemsToChild(childId, gems) { success ->
                        if (success) {
                            Toast.makeText(context, "Gems added successfully!", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        } else {
                            Toast.makeText(context, "Failed to add gems", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }
    )
}