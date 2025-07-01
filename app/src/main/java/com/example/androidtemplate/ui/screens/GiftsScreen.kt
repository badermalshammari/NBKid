package com.example.androidtemplate.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidtemplate.R
import com.example.androidtemplate.data.dtos.ItemsTypes
import com.example.androidtemplate.ui.composables.CreditCardComposable
import com.example.androidtemplate.ui.composables.Header
import com.example.androidtemplate.ui.composables.OrderItemCard
import com.example.androidtemplate.ui.composables.ParentStoreItemCard
import com.example.androidtemplate.viewmodels.CardScreenViewModel
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import com.example.androidtemplate.viewmodels.WalletViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GiftsScreen(
    cardId: Long,
    cardViewModel: CardScreenViewModel,
    walletViewModel: WalletViewModel,
    nbkidsViewModel: NBKidsViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val cards by cardViewModel.cards.collectAsState()
    val card = cards.find { it.cardId == cardId }
    val wallet by walletViewModel.walletState.collectAsState()
    val storeItems by nbkidsViewModel.storeitems
    val orders = walletViewModel.orders
    val child = wallet?.child
    val childId = wallet?.child?.childId ?: return

    val isLoadingOrders = walletViewModel.is_Loading
    val errorOrders = walletViewModel.error_Message

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoadingOrders)

    LaunchedEffect(cardId) {
        nbkidsViewModel.fetchStoreItems(childId)
    }

    LaunchedEffect(child) {
        child?.childId?.let {
            nbkidsViewModel.fetchStoreItems(it)
            walletViewModel.fetchWallet(it)
            walletViewModel.fetchOrders(it)
        }
    }
    var searchQuery by remember { mutableStateOf("") }
    var filterHidden by remember { mutableStateOf("All") }
    var selectedType by remember { mutableStateOf<ItemsTypes?>(null) }

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Store", "Ordered Items")

    if (card == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Card not found", color = Color.Red)
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

            TabRow(selectedTabIndex = selectedTabIndex) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title, fontWeight = FontWeight.Bold) }
                    )
                }
            }
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    child?.childId?.let {
                        walletViewModel.fetchOrders(it)
                    }
                },
                modifier = Modifier.fillMaxSize()
            ) {
                when (selectedTabIndex) {
                    0 -> {

                        Column(
                            Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {

                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                placeholder = { Text("Search...") },
                                singleLine = true,
                                modifier = Modifier
                                    .height(65.dp)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp)),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFFFFFFFF),
                                    unfocusedBorderColor = Color.White,
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    cursorColor = Color.Black,
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black
                                ),
                            )

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                DropdownMenuBox(
                                    label = "Filter",
                                    options = listOf("All", "Visible Only", "Hidden Only"),
                                    selected = filterHidden,
                                    onSelectedChange = { filterHidden = it }
                                )

                                DropdownMenuBox(
                                    label = "Type",
                                    options = ItemsTypes.values().map { it.name },
                                    selected = selectedType?.name ?: "ALL",
                                    onSelectedChange = { typeStr ->
                                        selectedType =
                                            if (typeStr == "ALL") null else ItemsTypes.valueOf(
                                                typeStr
                                            )
                                    }
                                )
                            }

                            Divider()

                            val filteredItems = storeItems.filter { item ->
                                val matchesQuery =
                                    item.globalItem.name.contains(searchQuery, ignoreCase = true)
                                val matchesFilter = when (filterHidden) {
                                    "Visible Only" -> item.isHidden == false
                                    "Hidden Only" -> item.isHidden == true
                                    else -> true
                                }
                                val matchesType =
                                    selectedType?.let { it.name == item.globalItem.type } ?: true
                                matchesQuery && matchesFilter && matchesType
                            }

                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(filteredItems) { item ->
                                    val imageResId = remember(item.globalItem.photo) {
                                        val resId = context.resources.getIdentifier(
                                            item.globalItem.photo,
                                            "drawable",
                                            context.packageName
                                        )
                                        if (resId == 0) R.drawable.nbkidz_logo_white else resId
                                    }

                                    ParentStoreItemCard(
                                        item = item,
                                        imageResId = imageResId,
                                        onToggleHidden = {
                                            nbkidsViewModel.toggleItemHidden(childId, it.id)
                                        }
                                    )
                                }
                            }
                        }
                    }

                    1 -> {

                                LazyColumn(
                                    verticalArrangement = Arrangement.spacedBy(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    items(orders) { order ->
                                        OrderItemCard(order)
                                    }
                                }
                            }
                        }
                    }
                }
            }

    }


@Composable
fun DropdownMenuBox(label: String, options: List<String>, selected: String, onSelectedChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        TextButton(onClick = { expanded = true }) {
            Text("$label: $selected")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach {
                DropdownMenuItem(onClick = {
                    onSelectedChange(it)
                    expanded = false
                }, text = { Text(it) })
            }
        }
    }
}