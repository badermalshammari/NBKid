package com.example.androidtemplate.ui.screens

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.androidtemplate.R
import com.example.androidtemplate.data.dtos.CreateChildRequest
import com.example.androidtemplate.data.dtos.CreateParentCardRequest
import com.example.androidtemplate.navigation.Screen
import com.example.androidtemplate.ui.composables.GenderCard
import com.example.androidtemplate.ui.composables.LoadingIndicator
import com.example.androidtemplate.viewmodels.CardScreenViewModel
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewParentAccount(
    mainViewModel: NBKidsViewModel,
    cardViewModel: CardScreenViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val accountType = listOf("Saving", "Salary", "Home")
    var selectedAccountType by rememberSaveable { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val selectedCard = rememberSaveable { mutableStateOf("parentcard_1") }

    val cardDesigns = listOf("parentcard_1", "parentcard_2", "parentcard_3", "parentcard_4")
    val cardImages = listOf(
        R.drawable.parentcard_1_design,
        R.drawable.parentcard_2_design,
        R.drawable.parentcard_3_design,
        R.drawable.parentcard_4_design
    )

    val isLoading = mainViewModel.isLoading
    val showError = remember { mutableStateOf<String?>(null) }


    if (showError.value != null) {
        LaunchedEffect(showError.value) {
            Toast.makeText(context, showError.value, Toast.LENGTH_LONG).show()
            showError.value = null
        }
    }

    if (isLoading) {
        LoadingIndicator()
        return
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Create New Account", fontWeight = FontWeight.Black) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            verticalArrangement = Arrangement.Center

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ) {

                Spacer(Modifier.height(24.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = selectedAccountType,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Account Type") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(50.dp),
                        colors = textFieldColors()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        accountType.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) },
                                onClick = {
                                    selectedAccountType = type
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                Text("Card Design", style = MaterialTheme.typography.headlineSmall)

                Spacer(Modifier.height(12.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    itemsIndexed(cardImages) { index, resId ->
                        Image(
                            painter = painterResource(id = resId),
                            contentDescription = null,
                            modifier = Modifier
                                .size(width = 300.dp, height = 180.dp)
                                .clickable { selectedCard.value = cardDesigns[index] }
                                .border(
                                    width = if (selectedCard.value == cardDesigns[index]) 4.dp else 0.dp,
                                    color = Color(0xFF3875A7),
                                    shape = RoundedCornerShape(20.dp)
                                )
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .width(200.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = (listOf(Color(0xFF8E44AD), Color(0xFFE74C3C))
                            )
                            )
                        )
                        .clickable {
                            if (selectedAccountType.isBlank()) {
                                showError.value = "Please fill all fields"
                                return@clickable
                            }

                            val request = CreateParentCardRequest(
                                parentId = mainViewModel.user?.parentId.toString(),
                                cardDesign = selectedCard.value
                            )

                            cardViewModel.createParentCard(
                                request = request,
                                onSuccess = {
                                    navController.navigate(route = Screen.ParentCardsScreen.route)
                                },
                                onError = {
                                    showError.value = it
                                }
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Add My Card",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun textFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Color(0xFF3875A7),
    unfocusedBorderColor = Color.LightGray,
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    cursorColor = Color.Black
)