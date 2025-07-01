package com.example.androidtemplate.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidtemplate.R
import com.example.androidtemplate.navigation.Screen
import com.example.androidtemplate.ui.composables.*
import com.example.androidtemplate.utils.Logout
import com.example.androidtemplate.viewmodels.CardScreenViewModel
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import com.example.androidtemplate.viewmodels.WalletViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentCardsScreen(
    mainViewModel: NBKidsViewModel,
    cardViewModel: CardScreenViewModel,
    walletViewModel: WalletViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val parentId = mainViewModel.user?.parentId

    var showCardDialog by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf("Wallet") }
    val cards by cardViewModel.cards.collectAsState()
    val displayZuzu by cardViewModel.displayZuzu.collectAsState()
    val wallet by walletViewModel.walletState.collectAsState()
    val selectedCard by cardViewModel.selectedCard.collectAsState()

    val isRefreshing = cardViewModel.isLoading

    val parentCards = cards.filter { it.isParentCard }
    val kidCards = cards.filter { !it.isParentCard }

    LaunchedEffect(parentId) {
        parentId?.let { cardViewModel.fetchCards(it) }

    }

    LaunchedEffect(selectedCard) {
        val card = selectedCard
        if (card != null && !card.isParentCard && card.childId != null) {
            walletViewModel.fetchWallet(card.childId)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("YOUR CARDS", fontWeight = FontWeight.Black) },
                navigationIcon = {
                    IconButton(onClick = { Logout(mainViewModel, navController) }) {
                        Icon(Icons.Default.Logout, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        Toast.makeText(context, "Refreshing...", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        },
        bottomBar = {
            FloatUp {
                ParentBottomNavBar(selected = "Person") { item ->
                    selectedTab = item
                    when (item) {
                        "Person" -> navController.navigate(Screen.ParentCardsScreen.route)
                        "Switch" -> navController.navigate(Screen.SelectKidScreen.route)
                        "Settings" -> navController.navigate(Screen.CardSettingsScreen.route)
                    }

                }
            }
        }
    ) { padding ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { parentId?.let { cardViewModel.fetchCards(it) } }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {

                MovingBackground(color = Color(0xFF32658F), xAxis = 200, size = 600)

                LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Text("Personal Cards", color = Color.Gray, modifier = Modifier.padding(16.dp)
                    )
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(230.dp)
                            .background(color = Color(0x2232658F)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        itemsIndexed(parentCards) { index, card ->
                            val isFirst = index == 0

                            Box(
                                modifier = Modifier
                                    .width(300.dp)
                                    .height(200.dp)
                                    .fillMaxHeight()
                                    .then(
                                        if (isFirst) Modifier.padding(start = 12.dp)
                                        else Modifier.padding(start = 0.dp)
                                    )
                                    .clickable { cardViewModel.selectCard(card) }
                            ) {

                                CreditCardComposable(card)
                            }
                        }
                        item {
                            Box(
                                modifier = Modifier
                                    .width(300.dp)
                                    .height(200.dp)
                                    .fillMaxHeight()
                                    .padding(end = 12.dp)
                                    .clickable {
                                    navController.navigate(Screen.CreateNewParentAccount.route)
                                    }
                            ) {
                                AddnewCreditCardComposable(true)
                            }
                        }
                    }
                }
                if (displayZuzu) {
                    item {
                        Text("NBKidz Cards", color = Color.Gray, modifier = Modifier.padding(16.dp))
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(230.dp)
                                .background(color = Color(0x2232658F)),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            itemsIndexed(kidCards) { index, card ->
                                val isFirst = index == 0

                                Box(
                                    modifier = Modifier
                                        .width(300.dp)
                                        .height(200.dp)
                                        .fillMaxHeight()
                                        .then(
                                            if (isFirst) Modifier.padding(start = 16.dp)
                                            else Modifier.padding(start = 0.dp)
                                        )
                                        .clickable { cardViewModel.selectCard(card) }
                                ) {
                                    CreditCardComposable(card)
                                }
                            }
                            item {
                                if (kidCards.isNullOrEmpty()) {
                                    Box(
                                        modifier = Modifier
                                            .width(300.dp)
                                            .height(200.dp)
                                            .fillMaxHeight()
                                            .padding(start = 12.dp)
                                            .clickable {
                                                navController.navigate(Screen.CreateNewChildAccount.route)
                                            }
                                    ) {
                                        AddnewCreditCardComposable(false)
                                    }
                                }else{
                                    Box(
                                        modifier = Modifier
                                            .width(300.dp)
                                            .height(200.dp)
                                            .fillMaxHeight()
                                            .padding(end = 12.dp)
                                            .clickable {
                                                navController.navigate(Screen.CreateNewChildAccount.route)
                                            }
                                    ) {
                                        AddnewCreditCardComposable(false)
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    Column (modifier = Modifier.padding(16.dp)) {
                        Divider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = Color.Gray,
                            thickness = 1.dp
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    selectedCard?.cardHolderName?.uppercase() ?: "No Card Selected",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 30.sp,
                                    color = Color.Black
                                )
                                Text(
                                    "(${selectedCard?.accountNumber ?: "N/A"})",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray,
                                    fontSize = 15.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            if (selectedCard?.isParentCard == false) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        modifier = Modifier.weight(1f),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.points),
                                            contentDescription = "points",
                                            modifier = Modifier.size(40.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            "Points ${wallet?.pointsBalance ?: 0}",
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Row(
                                        modifier = Modifier.weight(1f),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.gems),
                                            contentDescription = "gems",
                                            modifier = Modifier.size(40.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            "Gems ${wallet?.gems ?: 0}",
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("Available Balance", color = Color.Gray)
                                        Text(
                                            "${selectedCard?.balance ?: 0.0} KD",
                                            style = MaterialTheme.typography.headlineMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            } else {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("Available Balance", color = Color.Gray)
                                        Text(
                                            "${selectedCard?.balance ?: 0.0} KD",
                                            style = MaterialTheme.typography.headlineMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        if(selectedCard?.isActive == true) {
                            ActionButtonItem(
                                backgroundColor = Color.Black,
                                icon = Icons.Default.Send,
                                label = "Transfer",
                                onClick = {
                                    navController.navigate(
                                        Screen.TransferScreen.route
                                    )
                                })
                        }
                        ActionButtonItem(
                            backgroundColor = Color.Black,
                            icon = Icons.Default.IosShare,
                            label = "Share",
                            onClick = {
                             selectedCard?.let { card ->
                            val shareText = """
                            Name: ${card.cardHolderName}
                            Account Number: ${card.accountNumber}
                            Card Number: **** **** **** ${card.cardNumber.takeLast(4)}
                            Expiry: ${card.expiryMonth}/${card.expiryYear}
                            Balance: ${card.balance} KD
                            Status: ${if (card.isActive) "Active" else "Disabled"}""".trimIndent()

                                    val intent = android.content.Intent().apply {
                                        action = android.content.Intent.ACTION_SEND
                                        putExtra(android.content.Intent.EXTRA_TEXT, shareText)
                                        type = "text/plain"
                                    }
                                    val chooser = android.content.Intent.createChooser(intent, "Share Card Info")
                                    context.startActivity(chooser)
                                }
                            }
                        )
                        if (selectedCard?.isParentCard == false && selectedCard?.isActive == true) {                            ActionButtonItem(
                                icon = Icons.Default.Description,
                                backgroundColor = Color.Black,
                                label = "Control",
                                onClick = {
                                    selectedCard?.cardId?.let {
                                        navController.navigate("enter_card_screen/$it")
                                    }
                                }
                            )

                        }
                    }
                }

                item {
                    SettingsToggle(
                        modifier = Modifier.padding(16.dp) ,
                        isChecked = displayZuzu,
                        onToggle = {
                            cardViewModel.toggleZuzuAccounts(it)
                        }
                    )
                }
                }
            }
        }
    }
}