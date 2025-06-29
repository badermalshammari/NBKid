package com.example.androidtemplate.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.androidtemplate.data.dtos.BankCardDto
import com.example.androidtemplate.data.requests.TransferRequest
import com.example.androidtemplate.viewmodels.CardScreenViewModel
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import com.example.androidtemplate.viewmodels.TransferViewModel
import java.math.BigDecimal
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferScreen(
    viewModel: TransferViewModel,
    navController: NavController,
    mainViewModel: NBKidsViewModel,
    cardViewModel: CardScreenViewModel
) {
    val parentId = mainViewModel.user?.parentId
    val parentCards by cardViewModel.cards.collectAsState()
    val context = LocalContext.current

    var selectedCard by remember { mutableStateOf<BankCardDto?>(null) }
    var toAccount by remember { mutableStateOf("") }
    var useManualEntry by remember { mutableStateOf(false) }
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var expandedFrom by remember { mutableStateOf(false) }
    var expandedTo by remember { mutableStateOf(false) }

    val isLoading = viewModel.isLoading
    val successMessage = viewModel.successMessage
    val errorMessage = viewModel.errorMessage

    val isFormValid = selectedCard != null && toAccount.isNotBlank() && amount.toBigDecimalOrNull() != null

    LaunchedEffect(parentId) {
        parentId?.let { cardViewModel.fetchCards(it) }
    }

    val availableToAccounts = parentCards.filter { it.accountNumber != selectedCard?.accountNumber }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transfer") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text("From Bank Account", style = MaterialTheme.typography.labelLarge)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .clickable { expandedFrom = true }
                    .padding(16.dp)
            ) {
                Text(
                    text = selectedCard?.let { "•••• ${it.accountNumber.toString().takeLast(4)} — ${it.cardHolderName}" }
                        ?: "Select an account",
                    color = if (selectedCard == null) Color.Gray else Color.Black
                )
            }

            DropdownMenu(expanded = expandedFrom, onDismissRequest = { expandedFrom = false }) {
                parentCards.forEach { card ->
                    DropdownMenuItem(
                        text = { Text("•••• ${card.accountNumber.toString().takeLast(4)} — ${card.cardHolderName}") },
                        onClick = {
                            selectedCard = card
                            expandedFrom = false
                        }
                    )
                }
            }

            selectedCard?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Available Balance", style = MaterialTheme.typography.labelSmall)
                        val formatted = DecimalFormat("#,##0.000").format(it.balance)
                        Text(
                            text = "KD $formatted",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            Text("To Bank Account", style = MaterialTheme.typography.labelLarge)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = useManualEntry,
                    onCheckedChange = { useManualEntry = it }
                )
                Text(if (!useManualEntry) "Enter manually" else "Choose from your accounts")
            }

            if (useManualEntry) {
                OutlinedTextField(
                    value = toAccount,
                    onValueChange = { toAccount = it },
                    label = { Text("Enter Account Number") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                        .clickable { expandedTo = true }
                        .padding(16.dp)
                ) {
                    Text(
                        text = if (toAccount.isBlank()) "Select an account"
                        else "•••• ${toAccount.takeLast(4)}",
                        color = if (toAccount.isBlank()) Color.Gray else Color.Black
                    )
                }

                DropdownMenu(
                    expanded = expandedTo,
                    onDismissRequest = { expandedTo = false }
                ) {
                    availableToAccounts.forEach { card ->
                        DropdownMenuItem(
                            text = { Text("•••• ${card.accountNumber.toString().takeLast(4)} — ${card.cardHolderName}") },
                            onClick = {
                                toAccount = card.accountNumber.toString()
                                expandedTo = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description (optional)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    val request = TransferRequest(
                        fromAccountNumber = selectedCard!!.accountNumber.toString(),
                        toAccountNumber = toAccount,
                        amount = amount.toBigDecimalOrNull() ?: BigDecimal.ZERO,
                        description = description.takeIf { it.isNotBlank() }
                    )
                    viewModel.performTransfer(request)
                },
                enabled = isFormValid && !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text("Transfer", fontSize = 16.sp)
                }
            }

            Spacer(Modifier.height(16.dp))

            errorMessage?.let {
                Text(it, color = Color.Red, textAlign = TextAlign.Center)
            }
        }

        if (successMessage != null) {
            Dialog(onDismissRequest = { viewModel.successMessage = null }) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    tonalElevation = 8.dp,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Success",
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier
                                .size(48.dp)
                                .background(Color(0x334CAF50), shape = CircleShape)
                                .padding(8.dp)
                        )
                        Spacer(Modifier.height(16.dp))
                        Text("Success", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text(successMessage, textAlign = TextAlign.Center, fontSize = 14.sp)
                        Spacer(Modifier.height(16.dp))
                        Button(onClick = {
                            viewModel.successMessage = null
                            navController.popBackStack()
                        }) {
                            Text("OK")
                        }
                    }
                }
            }
        }
    }
}