package com.example.androidtemplate.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.androidtemplate.ui.composables.ParentStoreItemCard
import com.example.androidtemplate.viewmodels.CardScreenViewModel
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import com.example.androidtemplate.viewmodels.WalletViewModel

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

    val childId = wallet?.child?.childId ?: return

    LaunchedEffect(cardId) {
        walletViewModel.fetchWallet(childId)
        nbkidsViewModel.fetchStoreItems(childId)
    }

    var searchQuery by remember { mutableStateOf("") }
    var filterHidden by remember { mutableStateOf("All") }
    var selectedType by remember { mutableStateOf<ItemsTypes?>(null) }

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
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search...") },
                modifier = Modifier.fillMaxWidth().background(color = Color.White, shape = RoundedCornerShape(20.dp))
            )

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
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
                        selectedType = if (typeStr == "ALL") null else ItemsTypes.valueOf(typeStr)
                    }
                )
            }

            Divider()

            val filteredItems = storeItems.filter { item ->
                val matchesQuery = item.globalItem.name.contains(searchQuery, ignoreCase = true)
                val matchesFilter = when (filterHidden) {
                    "Visible Only" -> item.isHidden == false
                    "Hidden Only" -> item.isHidden == true
                    else -> true
                }
                val matchesType = selectedType?.let { it.name == item.globalItem.type } ?: true
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