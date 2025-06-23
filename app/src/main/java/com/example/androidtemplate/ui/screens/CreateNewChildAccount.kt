package com.example.androidtemplate.ui.screens

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.androidtemplate.R
import com.example.androidtemplate.data.dtos.CreateChildRequest
import com.example.androidtemplate.navigation.Screen
import com.example.androidtemplate.ui.composables.GenderCard
import com.example.androidtemplate.ui.composables.LoadingIndicator
import com.example.androidtemplate.viewmodels.CardScreenViewModel
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import java.util.*

@Composable
fun CreateNewChildAccount(
    mainViewModel: NBKidsViewModel,
    cardViewModel: CardScreenViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val name = rememberSaveable { mutableStateOf("") }
    val civilId = rememberSaveable { mutableStateOf("") }
    val birthday = rememberSaveable { mutableStateOf("") }
    val gender = rememberSaveable { mutableStateOf("ZAINAH") }
    val selectedCard = rememberSaveable { mutableStateOf("kidcard_1") }

    val cardDesigns = listOf("kidcard_1", "kidcard_2", "kidcard_3", "kidcard_4")
    val cardImages = listOf(
        R.drawable.kidcard_1_design,
        R.drawable.kidcard_2_design,
        R.drawable.kidcard_3_design,
        R.drawable.kidcard_4_design
    )

    val isLoading = mainViewModel.isLoading
    val showError = remember { mutableStateOf<String?>(null) }

    // Date Picker
    val calendar = Calendar.getInstance()
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val formatted = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
                birthday.value = formatted
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    // Show Toast if error occurs
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

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Create New Account", style = MaterialTheme.typography.headlineSmall)

            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = { Text("Kid Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50.dp),
                colors = textFieldColors()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = civilId.value,
                onValueChange = { civilId.value = it },
                label = { Text("Civil ID") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50.dp),
                colors = textFieldColors()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = birthday.value,
                onValueChange = {birthday.value = it},
                label = { Text("Birthday") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { datePickerDialog.show() },
                shape = RoundedCornerShape(50.dp),
                colors = textFieldColors()
            )

            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                GenderCard(
                    name = "Zainah",
                    imageRes = R.drawable.zainah,
                    selected = gender.value == "ZAINAH",
                    onClick = { gender.value = "ZAINAH" }
                )
                GenderCard(
                    name = "Zain",
                    imageRes = R.drawable.zain,
                    selected = gender.value == "ZAIN",
                    onClick = { gender.value = "ZAIN" }
                )
            }

            Spacer(Modifier.height(32.dp))

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
                            colors = listOf(Color(0xFFC778DD), Color(0xFFFC6096))
                        )
                    )
                    .clickable {
                        if (name.value.isBlank() || civilId.value.isBlank() || birthday.value.isBlank()) {
                            showError.value = "Please fill all fields"
                            return@clickable
                        }

                        val request = CreateChildRequest(
                            name = name.value.trim(),
                            civilId = civilId.value.trim(),
                            birthday = birthday.value.trim(),
                            gender = gender.value
                        )

                        cardViewModel.createChild(
                            request = request,
                            onSuccess = {
                                navController.navigate(route = Screen.SignupSuccess.route)
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

@Composable
private fun textFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Color(0xFF3875A7),
    unfocusedBorderColor = Color.LightGray,
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    cursorColor = Color.Black
)