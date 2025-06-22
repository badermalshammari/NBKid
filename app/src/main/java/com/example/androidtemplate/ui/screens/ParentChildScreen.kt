package com.example.androidtemplate.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidtemplate.data.dtos.BankCardDto
import com.example.androidtemplate.data.dtos.Child
import com.example.androidtemplate.data.dtos.ChildDto
import com.example.androidtemplate.data.dtos.Parent
import com.example.androidtemplate.data.dtos.WalletResponseDto
import com.example.androidtemplate.ui.composables.ActionButtonData
import com.example.androidtemplate.ui.composables.ActionGrid
import com.example.androidtemplate.ui.composables.CreditCardComposable
import com.example.androidtemplate.ui.composables.FinancialDetailSection
import com.example.androidtemplate.viewmodels.CardScreenViewModel
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import com.example.androidtemplate.viewmodels.WalletViewModel
import java.math.BigDecimal

//@Composable
//fun ParentChildScreen(
//    nbViewModel: NBKidsViewModel,
//    cardViewModel: CardScreenViewModel = CardScreenViewModel(LocalContext.current),
//    walletViewModel: WalletViewModel = WalletViewModel(LocalContext.current),
//    onAddBalanceClick: () -> Unit = {},
//    onSettingsClick: () -> Unit = {},
//    onTransferClick: () -> Unit = {},
//    onLeaderboardClick: () -> Unit = {},
//    onAddTaskClick: () -> Unit = {},
//    onGiftsClick: () -> Unit = {}
//) {
//    val parent = nbViewModel.user
//    val selectedCard = cardViewModel.selectedCard.collectAsState()
//    val selectedChild = nbViewModel.selectedChild
//    val wallet = walletViewModel.wallet
//    val isWalletLoading = walletViewModel.isLoading
//    val isAccountLoaded = nbViewModel.isAccountLoaded
//
//    // Fetch parent cards
//    LaunchedEffect(parent?.parentId) {
//        parent?.parentId?.let { cardViewModel.fetchCards(it) }
//    }
//
//    // Fetch child wallet
//    LaunchedEffect(selectedChild?.childId) {
//        selectedChild?.childId?.let { walletViewModel.fetchWallet(it) }
//    }
//
//    if (!isAccountLoaded || selectedCard == null || (selectedChild != null && wallet == null && isWalletLoading)) {
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            CircularProgressIndicator()
//        }
//    } else {
//        val card = selectedCard!!
//        val balance = card.value?.balance
//        val gems = wallet?.gems ?: 0
//        val points = wallet?.pointsBalance ?: 0
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White)
//                .padding(horizontal = 16.dp, vertical = 24.dp)
//        ) {
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            CreditCardComposable(card = card.value!!)
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            FinancialDetailSection(balance = balance.toString(), gems = gems, points = points)
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            ActionGrid(
//                buttons = listOf(
//                    ActionButtonData("Add Balance", Icons.Default.Add, onAddBalanceClick),
//                    ActionButtonData("Settings", Icons.Default.Settings, onSettingsClick),
//                    ActionButtonData("Transfer", Icons.Default.Send, onTransferClick),
//                    ActionButtonData("Leaderboard", Icons.Default.Leaderboard, onLeaderboardClick),
//                    ActionButtonData("Add Task", Icons.Default.PlaylistAdd, onAddTaskClick),
//                    ActionButtonData("Gifts", Icons.Default.CardGiftcard, onGiftsClick)
//                )
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            Text("Latest Transactions", fontWeight = FontWeight.Bold)
//            // TODO: Transaction list here
//        }
//    }
//}

@Composable
fun ParentChildScreen(
    onAddBalanceClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onTransferClick: () -> Unit = {},
    onLeaderboardClick: () -> Unit = {},
    onAddTaskClick: () -> Unit = {},
    onGiftsClick: () -> Unit = {}
) {
    // Fake card data
    val dummyCard = BankCardDto(
        cardId = 1L,
        cardNumber = "1234567812345678",
        accountNumber = 12345678910,
        cardHolderName = "Khaled Jr.",
        expiryMonth = 12,
        expiryYear = 2028,
        cvv = "123",
        balance = BigDecimal("199.990"),
        cardDesign = null
    )

    val gems = 2500
    val points = 12000
    val balance = dummyCard.balance.toPlainString()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        // Fake top bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = null)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Khaled Jr. Account", fontWeight = FontWeight.Bold)
                Text("(12345678910)", fontSize = 12.sp)
            }
            Icon(Icons.Default.Notifications, contentDescription = null)
        }

        Spacer(modifier = Modifier.height(16.dp))

        CreditCardComposable(card = dummyCard)

        Spacer(modifier = Modifier.height(24.dp))

        FinancialDetailSection(balance = balance, gems = gems, points = points)

        Spacer(modifier = Modifier.height(24.dp))

        ActionGrid(
            buttons = listOf(
                ActionButtonData("Add Balance", Icons.Default.Add, onAddBalanceClick),
                ActionButtonData("Settings", Icons.Default.Settings, onSettingsClick),
                ActionButtonData("Transfer", Icons.Default.Send, onTransferClick),
                ActionButtonData("Leaderboard", Icons.Default.Leaderboard, onLeaderboardClick),
                ActionButtonData("Add Task", Icons.Default.PlaylistAdd, onAddTaskClick),
                ActionButtonData("Gifts", Icons.Default.CardGiftcard, onGiftsClick)
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Latest Transactions", fontWeight = FontWeight.Bold)
        // TODO: Add dummy transactions list if needed
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewParentChildScreen() {
    ParentChildScreen()
}
